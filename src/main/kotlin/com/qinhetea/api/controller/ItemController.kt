package com.qinhetea.api.controller

import com.qinhetea.api.entity.User
import com.qinhetea.api.repository.ItemRepository
import org.springframework.data.rest.webmvc.RepositoryRestController
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RepositoryRestController
@RequestMapping("/items")
class ItemController(val itemRepository: ItemRepository) {

  @GetMapping("/count")
  @Secured("ROLE_${User.Role.ADMIN}")
  fun count() = ResponseEntity.ok(mapOf("count" to itemRepository.count()))

}
