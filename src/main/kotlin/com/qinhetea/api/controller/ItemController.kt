package com.qinhetea.api.controller

import com.qinhetea.api.entity.Item
import com.qinhetea.api.repository.ItemRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/items")
class ItemController(val repository: ItemRepository) {

  @GetMapping("/")
  fun findAll(): Iterable<Item> = repository.findAll()

  @GetMapping("/{id}")
  fun findById(@PathVariable id: Long) = repository.findById(id)!!

  @GetMapping("/q")
  fun findByName(@RequestParam name: String) = repository.findByName(name)

  @PostMapping("/")
  fun save(@RequestBody item: Item) = repository.save(item)!!

}
