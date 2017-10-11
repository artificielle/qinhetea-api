package com.qinhetea.api.controller

import com.qinhetea.api.repository.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/self")
class SelfController(val userRepository: UserRepository) {

  @GetMapping("")
  fun get(principal: Principal) = userRepository.findByUsername(principal.name)

}
