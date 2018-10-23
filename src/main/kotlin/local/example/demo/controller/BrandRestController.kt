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

import local.example.demo.assembler.BrandResourceAssembler
import local.example.demo.exception.BrandNotFoundException
import local.example.demo.model.Brand
import local.example.demo.repository.BrandRepository
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URISyntaxException

@RestController
@RequestMapping("/api/brands")
class BrandRestController internal constructor(
        val brandRepository: BrandRepository,
        val brandResourceAssembler: BrandResourceAssembler
) {
    @GetMapping("/{id}")
    @Throws(URISyntaxException::class)
    internal fun read(@PathVariable id: Long?): Resource<Brand> {
        val brand = brandRepository.findById(id!!)
                .orElseThrow{ BrandNotFoundException(id) }
        return brandResourceAssembler.toResource(brand)
    }

    @GetMapping
    @Throws(URISyntaxException::class)
    internal fun readAll(): Resources<Resource<Brand>> {
        val brands = brandRepository.findAll()
                .asSequence()
                .map(brandResourceAssembler::toResource).toList()
        return Resources(brands,
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(AddressRestController::class.java)
                        .readAll()).withSelfRel())
    }
}
