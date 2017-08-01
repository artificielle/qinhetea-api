package com.qinhetea.api.repository

import com.qinhetea.api.entity.Item
import org.springframework.data.repository.CrudRepository

interface ItemRepository : CrudRepository<Item, Long> {

  fun findByName(name: String): Iterable<Item>

}
