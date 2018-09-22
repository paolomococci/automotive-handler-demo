/**
 *
 * Copyright 2018 paolo mococci
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package local.example.demo.controller

import local.example.demo.assembler.OwnerResourceAssembler
import local.example.demo.exception.OwnerNotFoundException
import local.example.demo.model.Owner
import local.example.demo.repository.OwnerRepository
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.net.URISyntaxException

@RestController
@RequestMapping("/api/owners")
class OwnerRestController internal constructor(
        private val ownerRepository: OwnerRepository,
        private val ownerResourceAssembler: OwnerResourceAssembler
) {

    @PostMapping
    @Throws(URISyntaxException::class)
    internal fun create(@RequestBody owner: Owner): ResponseEntity<Resource<Owner>> {
        val resource = ownerResourceAssembler.toResource(ownerRepository.saveAndFlush(owner))
        return ResponseEntity.created(URI(resource.id.expand().href)).body(resource)
    }

    @GetMapping("/{id}")
    @Throws(URISyntaxException::class)
    internal fun read(@PathVariable id: Long?): Resource<Owner> {
        val owner = ownerRepository.findById(id!!)
                .orElseThrow { OwnerNotFoundException(id) }
        return ownerResourceAssembler.toResource(owner)
    }

    @GetMapping
    @Throws(URISyntaxException::class)
    internal fun readAll(): Resources<Resource<Owner>> {
        val owners = ownerRepository.findAll()
                .map(ownerResourceAssembler::toResource).toList()
        return Resources(owners,
                linkTo(methodOn(OwnerRestController::class.java)
                        .readAll()).withSelfRel())
    }

    @PutMapping("/{id}")
    @Throws(URISyntaxException::class)
    internal fun update(@RequestBody owner: Owner, @PathVariable id: Long?): ResponseEntity<*> {
        val updated = ownerRepository.findById(id!!)
                .map { temp ->
                    temp.firstName = owner.firstName
                    temp.lastName = owner.lastName
                    ownerRepository.saveAndFlush(temp)
                }
                .orElseGet {
                    ownerRepository.saveAndFlush(owner)
                }
        val resource = ownerResourceAssembler.toResource(updated)
        return ResponseEntity.created(URI(resource.id.expand().href)).body(resource)
    }

    @PatchMapping("/{id}")
    @Throws(URISyntaxException::class)
    internal fun partialUpdate(@RequestBody owner: Owner, @PathVariable id: Long?): ResponseEntity<*> {
        val updated = ownerRepository.findById(id!!)
                .map { temp ->
                    if (!owner.firstName.isNullOrBlank()) temp.firstName = owner.firstName
                    if (!owner.lastName.isNullOrBlank()) temp.lastName = owner.lastName
                    ownerRepository.saveAndFlush(temp)
                }
                .orElseGet {
                    ownerRepository.saveAndFlush(owner)
                }
        val resource = ownerResourceAssembler.toResource(updated)
        return ResponseEntity.created(URI(resource.id.expand().href)).body(resource)
    }

    @DeleteMapping("/{id}")
    @Throws(URISyntaxException::class)
    internal fun cancel(@PathVariable id: Long?): ResponseEntity<*> {
        if (id != null) {
            ownerRepository.deleteById(id)
        }
        return ResponseEntity.noContent().build<Any>()
    }
}
