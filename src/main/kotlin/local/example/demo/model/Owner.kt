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
import java.util.*
import javax.persistence.*

@Entity
class Owner {
    @Id @GeneratedValue val id: Long = 0
    @NaturalId @Column(nullable = false) val nickname: String = "j" + Date().time.toString()
    @Column(nullable = false) var name: String = ""
    @Column(nullable = false) var surname: String = ""
    @Column(nullable = false) var birthday: String = ""
    @OneToMany(mappedBy = "domicile") val domiciles: List<Address>? = null
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "vehicle_owner",
            joinColumns = [JoinColumn(name = "vehicle_id", referencedColumnName = "id")],
            inverseJoinColumns = [JoinColumn(name = "owner_id", referencedColumnName = "id")])
    val vehicles: List<Vehicle>? = null
    constructor()
}
