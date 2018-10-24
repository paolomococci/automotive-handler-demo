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
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URISyntaxException

@RestController
@RequestMapping("/api/vehicles")
class VehicleRestController internal constructor(
        private val vehicleRepository: VehicleRepository,
        private val vehicleResourceAssembler: VehicleResourceAssembler
) {
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
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(VehicleRestController::class.java)
                        .readAll()).withSelfRel())
    }

    @GetMapping("/model/{model}")
    @Throws(URISyntaxException::class)
    internal fun ListByModel(@PathVariable model: String?): Resources<Resource<Vehicle>> {
        val vehicles = vehicleRepository.searchByModel(model)
                .asSequence()
                .map(vehicleResourceAssembler::toResource).toList()
        return Resources(vehicles,
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(VehicleRestController::class.java)
                        .readAll()).withSelfRel())
    }
}
