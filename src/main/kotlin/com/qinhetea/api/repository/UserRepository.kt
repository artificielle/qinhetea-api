package com.qinhetea.api.repository

import com.qinhetea.api.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource(exported = false)
interface UserRepository : JpaRepository<User, Long> {

  fun findByUsername(username: String): Optional<User>

}
