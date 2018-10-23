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

package local.example.demo.association

import local.example.demo.AutomotiveHandlerApplication
import local.example.demo.repository.AddressRepository
import local.example.demo.repository.OwnerRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [AutomotiveHandlerApplication::class])
@AutoConfigureMockMvc
class OwnerAddressesAssociationMockMvcTests {

    val owner: String = """{"nickname":"martha89","name":"Martha","surname":"Miller","birthday":"1989/03/26"}"""
    val address1: String = """{"country":"Thailand","region":"north","city":"Chiang Mai","zip":"50000","avenue":"Ling Road","civic":"42","internal":"D"}"""
    val address2: String = """{"country":"Thailand","region":"east","city":"Rayong","zip":"21000","avenue":"Me Plaza","civic":"16","internal":"B"}"""

    @Autowired
    private val mockMvc: MockMvc? = null

    @Autowired
    val ownerRepository: OwnerRepository? = null

    @Autowired
    val addressRepository: AddressRepository? = null

    @Before
    fun initialize() {
        ownerRepository?.deleteAll()
        addressRepository?.deleteAll()
    }

    @Test
    fun `verify owner-addresses associations`() {
        val address1MockMvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/addresses")
                .content(address1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated).andReturn()
        val address2MockMvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addresses")
                .content(address2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated).andReturn()
        val ownerMockMvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/owners")
                .content(owner).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated).andReturn()
        val address1Result = address1MockMvcResult.response.getHeader("Location")
        val address2Result = address2MockMvcResult.response.getHeader("Location")
        val ownerResult = ownerMockMvcResult.response.getHeader("Location")
        mockMvc.perform(MockMvcRequestBuilders.put(address1Result!! + "/domicile")
                .content(ownerResult!!).contentType("text/uri-list"))
                .andExpect(MockMvcResultMatchers.status().isNoContent)
        mockMvc.perform(MockMvcRequestBuilders.put(address2Result!! + "/domicile")
                .content(ownerResult).contentType("text/uri-list"))
                .andExpect(MockMvcResultMatchers.status().isNoContent)
        mockMvc.perform(MockMvcRequestBuilders.get("$ownerResult/domiciles"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.addresses[0].city")
                        .value("Chiang Mai"))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.addresses[1].city")
                        .value("Rayong"))
    }
}
