package com.qinhetea.api.controller

import com.qinhetea.api.entity.User
import com.qinhetea.api.repository.UserRepository
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
  fun create(@RequestBody user: User) = ResponseEntity.ok(
    repository.save(user.encodePassword())
  )

}
