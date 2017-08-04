package com.qinhetea.api.repository

import com.qinhetea.api.entity.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface ItemRepository : JpaRepository<Item, Long> {

  fun findByName(@Param("name") name: String): Optional<Item>

}
