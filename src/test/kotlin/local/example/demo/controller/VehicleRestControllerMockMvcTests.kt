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

import local.example.demo.AutomotiveHandlerApplication
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [AutomotiveHandlerApplication::class])
@AutoConfigureMockMvc
class VehicleRestControllerMockMvcTests {

    val item1: String = """{"chassis":"NR5679816324851","model":"PC2000A","name":"Path Cruiser 2000","plate":"BULL459871"}"""
    val item2: String = """{"chassis":"NR8512987432681","model":"PC2500A","name":"Path Cruiser 2500","plate":"BULL784509"}"""
    val item3: String = """{"chassis":"NR4598713258745","model":"PC2500A","name":"Path Cruiser 2500","plate":"BULL569713"}"""

    @Autowired
    val mockMvc: MockMvc? = null

    @Autowired
    val vehicleRepository: VehicleRepository? = null

    @Before
    fun initialize() {
        vehicleRepository?.deleteAll()
        mockMvc!!.perform(MockMvcRequestBuilders.post("/vehicles")
                .content(item1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
        mockMvc!!.perform(MockMvcRequestBuilders.post("/vehicles")
                .content(item2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
        mockMvc!!.perform(MockMvcRequestBuilders.post("/vehicles")
                .content(item3).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    @Throws(Exception::class)
    fun `verify existence`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/vehicles"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.self").exists())
    }

    @Test
    @Throws(Exception::class)
    fun `retrieve list by surname`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/vehicles/model/PC2500A"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.vehicles[0].plate").value("BULL784509"))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.vehicles[1].plate").value("BULL569713"))
    }
}
