package com.qinhetea.api.controller

import com.qinhetea.api.entity.User
import com.qinhetea.api.repository.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(val repository: UserRepository) {

  @GetMapping("")
  fun findAll(): Iterable<User> = repository.findAll()

}
