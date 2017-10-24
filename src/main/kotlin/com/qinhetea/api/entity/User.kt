package com.qinhetea.api.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*
import org.springframework.security.core.userdetails.User as UserDetailsUser

typealias UserRole = String

@Entity
@Table(name = "application_user")
data class User(
  val nickname: String? = null,
  @Column(unique = true)
  val username: String,
  @JsonIgnore
  val password: String,
  val roles: Array<UserRole> = arrayOf(Role.USER),
  @Id @GeneratedValue
  val id: Long = 0
) {

  fun toUserDetails() = UserDetailsUser(
    username, password,
    true, true, true, true,
    roles.map { SimpleGrantedAuthority("ROLE_$it") }
  )

  fun encodePassword() = copy(password = encoder.encode(password))

  fun erasePassword() = copy(password = "")

  override fun equals(other: Any?): Boolean = when (other) {
    is User -> id == other.id
    else -> false
  }

  override fun hashCode() = id.toInt()

  object Role {
    const val USER = "USER"
    const val ADMIN = "ADMIN"
    const val ACTUATOR = "ACTUATOR"
  }

  companion object {
    val encoder = BCryptPasswordEncoder()
  }

}
