package com.qinhetea.api.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Item(
  val name: String,
  val content: String = "",
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  val id: Long = 0
)
