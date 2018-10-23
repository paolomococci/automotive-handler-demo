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
class BrandRepositoryMockMvcTests {

    val item: String = """{"code":"TC987325714","name":"Acme Five-Stars"}"""

    @Autowired
    val mockMvc: MockMvc? = null

    @Autowired
    val brandRepository: BrandRepository? = null

    @Before
    fun initialize() {
        brandRepository?.deleteAll()
    }

    @Test @Throws(Exception::class) fun `verify existence`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/brands"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.brands").exists())
    }

    @Test @Throws(Exception::class) fun `retrieve test`() {
        val mockMvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/brands")
                .content(item).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
        val result = mockMvcResult.response.getHeader("Location")
        mockMvc?.perform(MockMvcRequestBuilders.get(result!!))
                ?.andExpect(MockMvcResultMatchers.status().isOk)
                ?.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("TC987325714"))
                ?.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Acme Five-Stars"))
    }

    @Test @Throws(Exception::class) fun `partial update test`() {
        val mockMvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/brands")
                .content(item).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
        val result = mockMvcResult.response.getHeader("Location")
        mockMvc!!.perform(MockMvcRequestBuilders.patch(result!!)
                .content("{\"name\":\"Acme One\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent)
        mockMvc!!.perform(MockMvcRequestBuilders.get(result))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Acme One"))
    }

    @Test @Throws(Exception::class) fun `delete test`() {
        val mockMvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/brands")
                .content(item).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
        val result = mockMvcResult.response.getHeader("Location")
        mockMvc!!.perform(MockMvcRequestBuilders.delete(result!!))
        mockMvc!!.perform(MockMvcRequestBuilders.get(result))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}
