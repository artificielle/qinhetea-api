package com.qinhetea.api.controller

import com.qinhetea.api.entity.User
import com.qinhetea.api.entity.UserRole
import com.qinhetea.api.repository.UserRepository
import mu.KotlinLogging
import org.springframework.data.rest.webmvc.RepositoryRestController
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RepositoryRestController
@RequestMapping("/users")
class UserController(override val repository: UserRepository)
  : FuzzySearchController<User> {

  @PostMapping("")
  @Secured("ROLE_${User.Role.ADMIN}")
  fun create(@RequestBody userForm: UserForm) = ResponseEntity.ok(
    repository.save(userForm.collect().encodePassword())
  )

  @Suppress("ArrayInDataClass")
  data class UserForm(
    private val nickname: String?,
    private val username: String,
    private val password: String,
    private val roles: Array<UserRole>
  ) {
    fun collect() = User(
      nickname = nickname,
      username = username,
      password = password,
      roles = roles
    )
  }

}

private val logger = KotlinLogging.logger {}
