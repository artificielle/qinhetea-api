package com.qinhetea.api.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Product(
  val name: String = "",
  @ManyToOne
  val category: Category = Category(),
  @Id @GeneratedValue
  val id: Long = 0
)
