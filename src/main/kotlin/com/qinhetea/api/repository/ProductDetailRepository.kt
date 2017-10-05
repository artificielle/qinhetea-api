package com.qinhetea.api.repository

import com.qinhetea.api.entity.ProductDetail
import org.springframework.data.jpa.repository.JpaRepository

interface ProductDetailRepository : JpaRepository<ProductDetail, Long>
