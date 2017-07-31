package com.qinhetea.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/items")
class ItemController {

  @GetMapping("/")
  fun all() = "[..]"

  @GetMapping("/{id}")
  fun get(@PathVariable id: Long) = "Item(id = $id)"

}
