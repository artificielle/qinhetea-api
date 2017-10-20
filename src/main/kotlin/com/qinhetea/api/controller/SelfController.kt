package com.qinhetea.api.controller

import com.qinhetea.api.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/self")
class SelfController(val userRepository: UserRepository) {

  @GetMapping("")
  fun get(principal: Principal?) = when (principal) {
    null -> ResponseEntity.status(HttpStatus.FORBIDDEN).build<Unit>()
    else -> ResponseEntity.ok(userRepository.findByUsername(principal.name))
  }

}
