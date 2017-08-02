package com.qinhetea.api.entity

import javax.persistence.*

@Entity
data class Item(
  val name: String = "",
  @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
  val tags: MutableList<ItemTag> = mutableListOf(),
  @Id @GeneratedValue
  val id: Long = 0
)
