package com.qinhetea.api.repository

import com.qinhetea.api.entity.Item
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface ItemRepository : PagingAndSortingRepository<Item, Long> {

  fun findByName(@Param("name") name: String): Iterable<Item>

}
