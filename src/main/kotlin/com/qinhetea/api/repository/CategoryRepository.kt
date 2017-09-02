package com.qinhetea.api.repository

import com.qinhetea.api.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long>
