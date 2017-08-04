package com.qinhetea.api.controller

import com.qinhetea.api.repository.ItemRepository
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/items")
class ItemController(val repository: ItemRepository) {

  @GetMapping("/repository-count")
  @Secured("ROLE_ADMIN")
  fun count() = repository.count()

  // @GetMapping("/")
  // fun findAll(): Iterable<Item> = repository.findAll()

  // @GetMapping("/{id}")
  // fun findById(@PathVariable id: Long) = repository.findById(id)!!

  // @GetMapping("/q")
  // fun findByName(@RequestParam name: String) = repository.findByName(name)

  // @PostMapping("/")
  // fun save(@RequestBody item: Item) = repository.save(item)!!

}
