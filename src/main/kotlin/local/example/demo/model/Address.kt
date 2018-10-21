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

package local.example.demo.model

import org.hibernate.annotations.NaturalId
import javax.persistence.*

@Entity
class Address {
    @Id @GeneratedValue val id: Long = 0
    @Column var country: String? = null
    @Column var region: String? = null
    @Column var city: String? = null
    @Column var zip: String? = null
    @Column var avenue: String? = null
    @Column var civic: String? = null
    @Column var internal: String? = null
    @ManyToOne @JoinColumn(name = "factory_id") var filial: Brand? = null
    @ManyToOne @JoinColumn(name = "owner_id") var domicile: Owner? = null
    constructor()
}
