package com.qinhetea.api.controller

import com.qinhetea.api.entity.User
import com.qinhetea.api.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("/api/users")
class UserController(val repository: UserRepository) {

  @GetMapping("")
  @RolesAllowed("ADMIN")
  fun findAll(pageable: Pageable): Page<User> = repository.findAll(pageable)

  @PostMapping("/save")
  @RolesAllowed("ADMIN")
  fun save(user: User): User = repository.save(user)

  @PostMapping("/del/{id}")
  @RolesAllowed("ADMIN")
  fun del(@PathVariable id: Long): Boolean {
    repository.delete(User(id = id))
    val user: User? = repository.findById(id).orElse(null)
    return user === null
  }
}
