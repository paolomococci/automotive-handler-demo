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
import local.example.demo.repository.BrandRepository
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
class BrandRestControllerMockMvcTests {

    val item1: String = """{"code":"TC987325714","name":"Acme Super-Cars"}"""
    val item2: String = """{"code":"TC236574182","name":"Acme Five-Stars"}"""
    val item3: String = """{"code":"TC598741258","name":"Acme Wild-Road"}"""
    val item4: String = """{"code":"TC357842789","name":"Acme Four-Wheels"}"""

    @Autowired
    val mockMvc: MockMvc? = null

    @Autowired
    val brandRepository: BrandRepository? = null

    @Before
    fun initialize() {
        brandRepository?.deleteAll()
        mockMvc!!.perform(MockMvcRequestBuilders.post("/brands")
                .content(item1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
        mockMvc!!.perform(MockMvcRequestBuilders.post("/brands")
                .content(item2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
        mockMvc!!.perform(MockMvcRequestBuilders.post("/brands")
                .content(item3).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
        mockMvc!!.perform(MockMvcRequestBuilders.post("/brands")
                .content(item4).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    @Throws(Exception::class)
    fun `verify existence`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/brands"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.self").exists())
    }

    @Test
    @Throws(Exception::class)
    fun `retrieve list by code`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/brands/code/TC598741258"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.brands[0].name").value("Acme Wild-Road"))
    }
}
