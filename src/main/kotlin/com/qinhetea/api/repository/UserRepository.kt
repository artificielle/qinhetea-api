package com.qinhetea.api.repository

import com.qinhetea.api.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {

  fun findByUsername(username: String): Optional<User>

}
