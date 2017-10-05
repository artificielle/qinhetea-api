package com.qinhetea.api.controller

import com.qinhetea.api.entity.Shop
import com.qinhetea.api.repository.ShopRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("/api/shops")
class ShopController(val repository: ShopRepository) {

  @GetMapping("")
  fun findAll(pageable: Pageable): Page<Shop> = repository.findAll(pageable)

  @PostMapping("/save")
  @RolesAllowed("ADMIN")
  fun save(shop: Shop): Shop = repository.save(shop)

  @PostMapping("/del/{id}")
  @RolesAllowed("ADMIN")
  fun del(@PathVariable id: Long): Boolean {
    repository.delete(Shop(id = id))
    val shop: Shop? = repository.findById(id).orElse(null)
    return shop === null
  }

}
