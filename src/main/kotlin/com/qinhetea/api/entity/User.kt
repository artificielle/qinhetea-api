package com.qinhetea.api.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import org.springframework.security.core.userdetails.User as UserDetailsUser

typealias UserRole = String

@Entity
@Table(name = "sys_user")
data class User(
  val username: String? = null,
  val nickname: String? = null,
  @JsonIgnore
  val password: String? = null,
  val roles: Array<UserRole> = arrayOf(Role.USER),
  @Id @GeneratedValue
  val id: Long? = null
) {

  fun toUserDetails() = UserDetailsUser(
    username, password,
    true, true, true, true,
    roles.map { SimpleGrantedAuthority("ROLE_$it") }
  )

  fun encodePassword() = copy(password = encoder.encode(password))

  fun erasePassword() = copy(password = null)

  override fun equals(other: Any?): Boolean = when (other) {
    is User -> id == other.id
    else -> false
  }

  override fun hashCode() = id?.toInt() ?: 0

  object Role {
    const val USER = "USER"
    const val ADMIN = "ADMIN"
    const val ACTUATOR = "ACTUATOR"
  }

  companion object {
    val encoder = BCryptPasswordEncoder()
  }

}

