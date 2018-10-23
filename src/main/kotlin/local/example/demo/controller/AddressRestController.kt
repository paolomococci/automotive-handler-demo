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

import local.example.demo.assembler.AddressResourceAssembler
import local.example.demo.exception.AddressNotFoundException
import local.example.demo.model.Address
import local.example.demo.repository.AddressRepository
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URISyntaxException

@RestController
@RequestMapping("/api/addresses")
class AddressRestController internal constructor(
        val addressRepository: AddressRepository,
        val addressResourceAssembler: AddressResourceAssembler
){
    @GetMapping("/{id}")
    @Throws(URISyntaxException::class)
    internal fun read(@PathVariable id: Long?): Resource<Address> {
        val address = addressRepository.findById(id!!)
                .orElseThrow { AddressNotFoundException(id) }
        return addressResourceAssembler.toResource(address)
    }

    @GetMapping
    @Throws(URISyntaxException::class)
    internal fun readAll(): Resources<Resource<Address>> {
        val address = addressRepository.findAll()
                .asSequence()
                .map(addressResourceAssembler::toResource).toList()
        return Resources(address,
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(AddressRestController::class.java)
                        .readAll()).withSelfRel())
    }

    @GetMapping("/country/{country}")
    @Throws(URISyntaxException::class)
    internal fun listByCountry(@PathVariable country: String?): Resources<Resource<Address>> {
        val address = addressRepository.searchByCountry(country)
                .asSequence()
                .map(addressResourceAssembler::toResource).toList()
        return Resources(address,
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(AddressRestController::class.java)
                        .readAll()).withSelfRel())
    }

    @GetMapping("/region/{region}")
    @Throws(URISyntaxException::class)
    internal fun listByRegion(@PathVariable region: String?): Resources<Resource<Address>> {
        val address = addressRepository.searchByRegion(region)
                .asSequence()
                .map(addressResourceAssembler::toResource).toList()
        return Resources(address,
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(AddressRestController::class.java)
                        .readAll()).withSelfRel())
    }

    @GetMapping("/city/{city}")
    @Throws(URISyntaxException::class)
    internal fun listByCity(@PathVariable city: String?): Resources<Resource<Address>> {
        val address = addressRepository.searchByCity(city)
                .asSequence()
                .map(addressResourceAssembler::toResource).toList()
        return Resources(address,
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(AddressRestController::class.java)
                        .readAll()).withSelfRel())
    }

    @GetMapping("/zip/{zip}")
    @Throws(URISyntaxException::class)
    internal fun listByZip(@PathVariable zip: String?): Resources<Resource<Address>> {
        val address = addressRepository.searchByZip(zip)
                .asSequence()
                .map(addressResourceAssembler::toResource).toList()
        return Resources(address,
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(AddressRestController::class.java)
                        .readAll()).withSelfRel())
    }

    @GetMapping("/avenue/{avenue}")
    @Throws(URISyntaxException::class)
    internal fun listByAvenue(@PathVariable avenue: String?): Resources<Resource<Address>> {
        val address = addressRepository.searchByAvenue(avenue)
                .asSequence()
                .map(addressResourceAssembler::toResource).toList()
        return Resources(address,
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(AddressRestController::class.java)
                        .readAll()).withSelfRel())
    }

    @GetMapping("/civic/{civic}")
    @Throws(URISyntaxException::class)
    internal fun listByCivic(@PathVariable civic: String?): Resources<Resource<Address>> {
        val address = addressRepository.searchByCivic(civic)
                .asSequence()
                .map(addressResourceAssembler::toResource).toList()
        return Resources(address,
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(AddressRestController::class.java)
                        .readAll()).withSelfRel())
    }

    @GetMapping("/internal/{internal}")
    @Throws(URISyntaxException::class)
    internal fun listByInternal(@PathVariable internal: String?): Resources<Resource<Address>> {
        val address = addressRepository.searchByInternal(internal)
                .asSequence()
                .map(addressResourceAssembler::toResource).toList()
        return Resources(address,
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(AddressRestController::class.java)
                        .readAll()).withSelfRel())
    }
}
