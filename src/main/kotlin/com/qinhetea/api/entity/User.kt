package com.qinhetea.api.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import org.springframework.security.core.userdetails.User as UserDetailsUser

@Entity
data class User(
  val username: String = "",
  @JsonIgnore
  val password: String = "",
  val role: String = "USER",
  @Id @GeneratedValue
  val id: Long = 0
) {

  fun toUserDetails() = UserDetailsUser(
    username, password,
    true, true, true, true,
    AuthorityUtils.createAuthorityList("ROLE_$role")
  )

  fun encodePassword() = copy(password = encoder.encode(password))

  fun erasePassword() = copy(password = "")

  companion object {

    val encoder = BCryptPasswordEncoder()

  }

}

