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
import local.example.demo.repository.OwnerRepository
import local.example.demo.repository.VehicleRepository
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
class OwnerVehiclesAssociationMockMvcTests {

    val owner: String = """{"nickname":"martha89","name":"Martha","surname":"Miller","birthday":"1989/03/26"}"""
    val vehicle1: String = """{"chassis":"NR5679816324851","model":"PC2500A","name":"Path Cruiser 2500","plate":"BULL784509"}"""
    val vehicle2: String = """{"chassis":"NR5679816324970","model":"PC2800A","name":"Path Cruiser 2800","plate":"SUPER84509"}"""

    @Autowired
    private val mockMvc: MockMvc? = null

    @Autowired
    val ownerRepository: OwnerRepository? = null

    @Autowired
    val vehicleRepository: VehicleRepository? = null

    @Before
    fun initialize() {
        ownerRepository?.deleteAll()
        vehicleRepository?.deleteAll()
    }

    @Test
    fun `verify owner-vehicles associations`() {
        val vehicle1MockMvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/vehicles")
                .content(vehicle1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated).andReturn()
        val vehicle2MockMvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/vehicles")
                .content(vehicle2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated).andReturn()
        val ownerMockMvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/owners")
                .content(owner).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated).andReturn()
        val vehicle1Result = vehicle1MockMvcResult.response.getHeader("Location")
        val vehicle2Result = vehicle2MockMvcResult.response.getHeader("Location")
        val ownerResult = ownerMockMvcResult.response.getHeader("Location")
        mockMvc.perform(MockMvcRequestBuilders.put(ownerResult!! + "/vehicles")
                .content("$vehicle1Result\n$vehicle2Result").contentType("text/uri-list"))
                .andExpect(MockMvcResultMatchers.status().isNoContent)
        mockMvc.perform(MockMvcRequestBuilders.get("$ownerResult/vehicles"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.vehicles[0].plate")
                        .value("BULL784509"))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.vehicles[1].plate")
                        .value("SUPER84509"))
    }
}
