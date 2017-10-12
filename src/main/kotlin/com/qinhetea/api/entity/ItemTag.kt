package com.qinhetea.api.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class ItemTag(
  val content: String? = null,
  @Id @GeneratedValue
  val id: Long = 0
)
