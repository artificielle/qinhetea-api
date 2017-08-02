package com.qinhetea.api.repository

import com.qinhetea.api.entity.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.*

@RepositoryRestResource
interface ItemRepository : JpaRepository<Item, Long> {

  fun findByName(@Param("name") name: String): Optional<Item>

}
