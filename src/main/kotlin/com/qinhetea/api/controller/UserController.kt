package com.qinhetea.api.controller

import com.qinhetea.api.entity.User
import com.qinhetea.api.repository.UserRepository
import org.springframework.data.rest.webmvc.RepositoryRestController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RepositoryRestController
@RequestMapping("/users")
class UserController(override val repository: UserRepository)
  : FuzzySearchController<User> {

  @PostMapping("")
  fun create(@RequestBody user: User) =
    ResponseEntity.status(HttpStatus.CREATED).body(
      repository.save(user.encodePassword())
    )

}
