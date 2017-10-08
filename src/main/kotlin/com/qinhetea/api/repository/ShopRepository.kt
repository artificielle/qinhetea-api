package com.qinhetea.api.repository

import com.qinhetea.api.entity.Shop
import org.springframework.data.jpa.repository.JpaRepository

interface ShopRepository : JpaRepository<Shop, Long>
