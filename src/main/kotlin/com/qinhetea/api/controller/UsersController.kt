package com.qinhetea.api.controller

import com.qinhetea.api.repository.UserRepository
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/users")
class UsersController(val repository: UserRepository) {

  @GetMapping("/me")
  @Secured("ROLE_ADMIN")
  fun get(principal: Principal) = repository.findByUsername(principal.name)

}
