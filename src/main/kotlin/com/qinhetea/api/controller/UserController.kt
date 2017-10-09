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

  @GetMapping("/")
  @RolesAllowed(User.Role.ADMIN)
  fun findAll(pagination: Pageable): Page<User> =
    repository.findAll(pagination)

  @PostMapping("/save")
  @RolesAllowed(User.Role.ADMIN)
  fun save(user: User): User =
    repository.save(user)

  @PostMapping("/delete/{id}")
  @RolesAllowed(User.Role.ADMIN)
  fun delete(@PathVariable id: Long): Boolean {
    repository.deleteById(id)
    return !repository.findById(id).isPresent
  }

}
