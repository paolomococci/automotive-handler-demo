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
import org.hamcrest.Matchers
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
class AddressRepositoryMockMvcTests {

    val item: String = """{"country":"Thailand","region":"north","city":"Chang-mai","zip":"05774","avenue":"Chang Road","civic":"123","internal":"A"}"""

    @Autowired
    val mockMvc: MockMvc? = null

    @Autowired
    val addressRepository: AddressRepository? = null

    @Before
    fun initialize() {
        addressRepository?.deleteAll()
    }

    @Test @Throws(Exception::class) fun `verify existence`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/addresses"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.addresses").exists())
    }

    @Test @Throws(Exception::class) fun `create test`() {
        mockMvc!!.perform(MockMvcRequestBuilders.post("/addresses")
                .content(item).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.containsString("addresses")))
    }

    @Test @Throws(Exception::class) fun `retrieve test`() {
        val mockMvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/addresses")
                .content(item).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
        val result = mockMvcResult.response.getHeader("Location")
        mockMvc?.perform(MockMvcRequestBuilders.get(result!!))
                ?.andExpect(MockMvcResultMatchers.status().isOk)
                ?.andExpect(MockMvcResultMatchers.jsonPath("$.country").value("Thailand"))
                ?.andExpect(MockMvcResultMatchers.jsonPath("$.region").value("north"))
                ?.andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Chang-mai"))
                ?.andExpect(MockMvcResultMatchers.jsonPath("$.zip").value("05774"))
                ?.andExpect(MockMvcResultMatchers.jsonPath("$.avenue").value("Chang Road"))
                ?.andExpect(MockMvcResultMatchers.jsonPath("$.civic").value("123"))
                ?.andExpect(MockMvcResultMatchers.jsonPath("$.internal").value("A"))
    }

    @Test @Throws(Exception::class) fun `partial update test`() {
        val mockMvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/addresses")
                .content(item).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
        val result = mockMvcResult.response.getHeader("Location")
        mockMvc!!.perform(MockMvcRequestBuilders.patch(result!!)
                .content("{\"internal\":\"1/A\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent)
        mockMvc!!.perform(MockMvcRequestBuilders.get(result))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.internal").value("1/A"))
    }

    @Test @Throws(Exception::class) fun `delete test`() {
        val mockMvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/addresses")
                .content(item).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
        val result = mockMvcResult.response.getHeader("Location")
        mockMvc!!.perform(MockMvcRequestBuilders.delete(result!!))
        mockMvc!!.perform(MockMvcRequestBuilders.get(result))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}