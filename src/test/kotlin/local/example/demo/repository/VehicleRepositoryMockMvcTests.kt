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

import local.example.demo.AutomotiveHandlerApplication
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
class VehicleRepositoryMockMvcTests {

    val item: String = """{"chassis":"NR5679816324851","model":"PC2500A","name":"Path Cruiser 2500","plate":"BULL784509"}"""

    @Autowired
    val mockMvc: MockMvc? = null

    @Autowired
    val vehicleRepository: VehicleRepository? = null

    @Before
    fun initialize() {
        vehicleRepository?.deleteAll()
    }

    @Test @Throws(Exception::class) fun `verify existence`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/vehicles"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.vehicles").exists())
    }

    @Test @Throws(Exception::class) fun `retrieve test`() {
        val mockMvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/vehicles")
                .content(item).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
        val result = mockMvcResult.response.getHeader("Location")
        mockMvc?.perform(MockMvcRequestBuilders.get(result!!))
                ?.andExpect(MockMvcResultMatchers.status().isOk)
                ?.andExpect(MockMvcResultMatchers.jsonPath("$.chassis").value("NR5679816324851"))
                ?.andExpect(MockMvcResultMatchers.jsonPath("$.model").value("PC2500A"))
                ?.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Path Cruiser 2500"))
                ?.andExpect(MockMvcResultMatchers.jsonPath("$.plate").value("BULL784509"))
    }

    @Test @Throws(Exception::class) fun `partial update test`() {
        val mockMvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/vehicles")
                .content(item).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
        val result = mockMvcResult.response.getHeader("Location")
        mockMvc!!.perform(MockMvcRequestBuilders.patch(result!!)
                .content("{\"plate\":\"BUN784511\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent)
        mockMvc!!.perform(MockMvcRequestBuilders.get(result))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.plate").value("BUN784511"))
    }

    @Test @Throws(Exception::class) fun `delete test`() {
        val mockMvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/vehicles")
                .content(item).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
        val result = mockMvcResult.response.getHeader("Location")
        mockMvc!!.perform(MockMvcRequestBuilders.delete(result!!))
        mockMvc!!.perform(MockMvcRequestBuilders.get(result))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}
