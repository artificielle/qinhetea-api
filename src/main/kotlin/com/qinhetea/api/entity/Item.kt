package com.qinhetea.api.entity

import javax.persistence.*

@Entity
data class Item(
  val name: String? = null,
  @OneToMany(fetch = FetchType.EAGER)
  val tags: MutableList<ItemTag>? = null,
  @Id @GeneratedValue
  val id: Long = 0
)
