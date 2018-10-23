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

package local.example.demo.repository

import local.example.demo.model.Address
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface AddressRepository : PagingAndSortingRepository<Address, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM ADDRESS WHERE COUNTRY LIKE ?1%")
    fun searchByCountry(@Param("country") country: String?): List<Address>
    @Query(nativeQuery = true, value = "SELECT * FROM ADDRESS WHERE REGION LIKE ?1%")
    fun searchByRegion(@Param("region") region: String?): List<Address>
    @Query(nativeQuery = true, value = "SELECT * FROM ADDRESS WHERE CITY LIKE ?1%")
    fun searchByCity(@Param("city") city: String?): List<Address>
    @Query(nativeQuery = true, value = "SELECT * FROM ADDRESS WHERE ZIP LIKE ?1%")
    fun searchByZip(@Param("zip") zip: String?): List<Address>
    @Query(nativeQuery = true, value = "SELECT * FROM ADDRESS WHERE AVENUE LIKE ?1%")
    fun searchByAvenue(@Param("avenue") avenue: String?): List<Address>
    @Query(nativeQuery = true, value = "SELECT * FROM ADDRESS WHERE CIVIC LIKE ?1%")
    fun searchByCivic(@Param("civic") civic: String?): List<Address>
    @Query(nativeQuery = true, value = "SELECT * FROM ADDRESS WHERE INTERNAL LIKE ?1%")
    fun searchByInternal(@Param("internal") internal: String?): List<Address>
}
