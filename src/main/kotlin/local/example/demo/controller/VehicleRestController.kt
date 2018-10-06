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

import local.example.demo.assembler.VehicleResourceAssembler
import local.example.demo.exception.VehicleNotFoundException
import local.example.demo.model.Vehicle
import local.example.demo.repository.VehicleRepository
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.net.URISyntaxException

@RestController
@RequestMapping("/api/vehicles")
class VehicleRestController internal constructor (
        private val vehicleRepository: VehicleRepository,
        private val vehicleResourceAssembler: VehicleResourceAssembler
) {

    @PostMapping
    @Throws(URISyntaxException::class)
    internal fun create(@RequestBody vehicle: Vehicle): ResponseEntity<Resource<Vehicle>> {
        val resource = vehicleResourceAssembler.toResource(vehicleRepository.saveAndFlush(vehicle))
        return ResponseEntity.created(URI(resource.id.expand().href)).body(resource)
    }

    @GetMapping("/{id}")
    @Throws(URISyntaxException::class)
    internal fun read(@PathVariable id: Long?): Resource<Vehicle> {
        val vehicle = vehicleRepository.findById(id!!)
                .orElseThrow { VehicleNotFoundException(id) }
        return vehicleResourceAssembler.toResource(vehicle)
    }

    @GetMapping
    @Throws(URISyntaxException::class)
    internal fun readAll(): Resources<Resource<Vehicle>> {
        val vehicles = vehicleRepository.findAll()
                .asSequence()
                .map(vehicleResourceAssembler::toResource).toList()
        return Resources(vehicles,
                linkTo(methodOn(VehicleRestController::class.java)
                        .readAll()).withSelfRel())
    }

    @PutMapping("/{id}")
    @Throws(URISyntaxException::class)
    internal fun update(@RequestBody vehicle: Vehicle, @PathVariable id: Long?): ResponseEntity<*> {
        val updated = vehicleRepository.findById(id!!)
                .map { temp ->
                    temp.brand = vehicle.brand
                    temp.name = vehicle.name
                    vehicleRepository.saveAndFlush(temp)
                }
                .orElseGet {
                    vehicleRepository.saveAndFlush(vehicle)
                }
        val resource = vehicleResourceAssembler.toResource(updated)
        return ResponseEntity.created(URI(resource.id.expand().href)).body(resource)
    }

    @PatchMapping("/{id}")
    @Throws(URISyntaxException::class)
    internal fun partialUpdate(@RequestBody vehicle: Vehicle, @PathVariable id: Long?): ResponseEntity<*> {
        val updated = vehicleRepository.findById(id!!)
                .map { temp ->
                    if (!vehicle.brand.isNullOrBlank()) temp.brand = vehicle.brand
                    if (!vehicle.name.isNullOrBlank()) temp.name = vehicle.name
                    vehicleRepository.saveAndFlush(temp)
                }
                .orElseGet {
                    vehicleRepository.saveAndFlush(vehicle)
                }
        val resource = vehicleResourceAssembler.toResource(updated)
        return ResponseEntity.created(URI(resource.id.expand().href)).body(resource)
    }

    @DeleteMapping("/{id}")
    @Throws(URISyntaxException::class)
    internal fun cancel(@PathVariable id: Long?): ResponseEntity<*> {
        if (id != null) {
            vehicleRepository.deleteById(id)
        }
        return ResponseEntity.noContent().build<Any>()
    }
}
