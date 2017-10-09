package com.qinhetea.api.controller

import com.qinhetea.api.entity.Shop
import com.qinhetea.api.entity.User
import com.qinhetea.api.repository.ShopRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("/api/shops")
class ShopController(val repository: ShopRepository) {

  @GetMapping("/")
  fun findAll(pagination: Pageable): Page<Shop> =
    repository.findAll(pagination)

  @PostMapping("/save")
  @RolesAllowed(User.Role.ADMIN)
  fun save(shop: Shop): Shop =
    repository.save(shop)

  @PostMapping("/delete/{id}")
  @RolesAllowed(User.Role.ADMIN)
  fun delete(@PathVariable id: Long): Boolean {
    repository.deleteById(id)
    return !repository.findById(id).isPresent
  }

}
