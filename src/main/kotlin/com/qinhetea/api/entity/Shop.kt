package com.qinhetea.api.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Shop(
  val name: String? = null,
  val address: String? = null,
  val description: String? = null,
  val telephone: String? = null,
  @Id @GeneratedValue
  val id: Long = 0
)
