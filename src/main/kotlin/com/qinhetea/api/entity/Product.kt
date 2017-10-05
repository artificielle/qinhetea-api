package com.qinhetea.api.entity

import javax.persistence.*

@Entity
data class Product(
  val name: String? = null,
  val subtitle: String? = null,
  val img: String? = null,
  @OneToOne(cascade = arrayOf(CascadeType.ALL))
  val detail: ProductDetail? = null,
  @ManyToOne(cascade = arrayOf(CascadeType.ALL))
  val category: Category? = null,
  @ManyToOne(cascade = arrayOf(CascadeType.ALL))
  val shop: Shop? = null,
  @Id @GeneratedValue
  val id: Long? = null
)
