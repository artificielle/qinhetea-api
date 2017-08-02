package com.qinhetea.api.repository

import com.qinhetea.api.entity.ItemTag
import org.springframework.data.jpa.repository.JpaRepository

interface ItemTagRepository : JpaRepository<ItemTag, Long>
