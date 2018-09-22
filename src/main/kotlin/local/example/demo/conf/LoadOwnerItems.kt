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

package local.example.demo.conf

import local.example.demo.model.Owner
import local.example.demo.repository.OwnerRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoadOwnerItems {

    @Bean
    internal fun initOwnerItems(ownerRepository: OwnerRepository) = CommandLineRunner {
        ownerRepository.save(Owner(firstName = "Kira", lastName = "Beauty"))
        ownerRepository.save(Owner(firstName = "Nike", lastName = "Lovely"))
        ownerRepository.save(Owner(firstName = "Zoe", lastName = "Fairy"))
    }
}
