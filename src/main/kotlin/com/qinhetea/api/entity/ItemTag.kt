package com.qinhetea.api.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class ItemTag(
  val content: String = "",
  @ManyToOne @JsonIgnore
  val item: Item = Item(),
  @Id @GeneratedValue
  val id: Long = 0
)
