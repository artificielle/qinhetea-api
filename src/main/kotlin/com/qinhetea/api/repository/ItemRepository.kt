package com.qinhetea.api.repository

import com.qinhetea.api.entity.Item
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface ItemRepository : JpaRepository<Item, Long> {

  fun findByNameContainingIgnoreCase(
    @Param("name") name: String,
    pagination: Pageable
  ): Page<Item>

}
