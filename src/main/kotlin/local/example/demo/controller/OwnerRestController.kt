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
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URISyntaxException

@RestController
@RequestMapping("/api/owners")
class OwnerRestController internal constructor(
        private val ownerRepository: OwnerRepository,
        private val ownerResourceAssembler: OwnerResourceAssembler
) {
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
                .asSequence()
                .map(ownerResourceAssembler::toResource).toList()
        return Resources(owners,
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(OwnerRestController::class.java)
                        .readAll()).withSelfRel())
    }
}
