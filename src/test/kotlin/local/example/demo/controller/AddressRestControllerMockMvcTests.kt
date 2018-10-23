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
import local.example.demo.repository.AddressRepository
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
class AddressRestControllerMockMvcTests {

    val item1: String = """{"country":"Italy","region":"Abruzzi","city":"Teramo","zip":"64010","avenue":"Corso Vecchio","civic":"26","internal":"1A"}"""
    val item2: String = """{"country":"Italy","region":"Piemonte","city":"Torino","zip":"10010","avenue":"Via Giuseppe Garibaldi","civic":"119","internal":"7B"}"""
    val item3: String = """{"country":"Italy","region":"Sardegna","city":"Sassari","zip":"07010","avenue":"Via Granatieri di Sardegna","civic":"26","internal":"12C"}"""
    val item4: String = """{"country":"Italy","region":"Toscana","city":"Firenze","zip":"50010","avenue":"Corso Vecchio","civic":"19","internal":"2F"}"""

    @Autowired
    val mockMvc: MockMvc? = null

    @Autowired
    val addressRepository: AddressRepository? = null

    @Before
    fun initialize() {
        addressRepository?.deleteAll()
        mockMvc!!.perform(MockMvcRequestBuilders.post("/addresses")
                .content(item1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
        mockMvc!!.perform(MockMvcRequestBuilders.post("/addresses")
                .content(item2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
        mockMvc!!.perform(MockMvcRequestBuilders.post("/addresses")
                .content(item3).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
        mockMvc!!.perform(MockMvcRequestBuilders.post("/addresses")
                .content(item4).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    @Throws(Exception::class)
    fun `verify existence`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/addresses"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.self").exists())
    }

    @Test
    @Throws(Exception::class)
    fun `retrieve list by country`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/addresses/country/Italy"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.addresses[0].region").value("Abruzzi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.addresses[1].region").value("Piemonte"))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.addresses[2].region").value("Sardegna"))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.addresses[3].region").value("Toscana"))
    }

    @Test
    @Throws(Exception::class)
    fun `retrieve list by region`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/addresses/region/Piemonte"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.addresses[0].city").value("Torino"))
    }

    @Test
    @Throws(Exception::class)
    fun `retrieve list by city`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/addresses/city/Firenze"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.addresses[0].region").value("Toscana"))
    }

    @Test
    @Throws(Exception::class)
    fun `retrieve list by zip`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/addresses/zip/10010"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.addresses[0].region").value("Piemonte"))
    }

    @Test
    @Throws(Exception::class)
    fun `retrieve list by avenue`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/addresses/avenue/Corso Vecchio"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.addresses[0].city").value("Teramo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.addresses[1].city").value("Firenze"))
    } // todo

    @Test
    @Throws(Exception::class)
    fun `retrieve list by civic`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/addresses/civic/26"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.addresses[0].city").value("Teramo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.addresses[1].city").value("Sassari"))
    }

    @Test
    @Throws(Exception::class)
    fun `retrieve list by internal`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/addresses/internal/2F"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.addresses[0].region").value("Toscana"))
    }
}
