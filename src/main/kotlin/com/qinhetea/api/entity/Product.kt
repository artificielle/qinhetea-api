package com.qinhetea.api.entity

import javax.persistence.*

@Entity
data class Product(
  val name: String? = null,
  val subtitle: String? = null,
  val img: String? = null,
  @OneToOne(cascade = arrayOf(CascadeType.ALL))
  val detail: ProductDetail? = null,
  @ManyToOne
  val category: Category? = null,
  @ManyToOne
  val shop: Shop? = null,
  @Id @GeneratedValue
  val id: Long = 0
)
