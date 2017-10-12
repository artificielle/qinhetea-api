package com.qinhetea.api.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class ProductDetail(
  /** unit: $0.01 */
  val price: Long? = null,
  val storage: Int? = null,
  val type: String? = null,
  val scale: String? = null,
  val ingredients: String? = null,
  val saleDate: String? = null,
  val store: String? = null,
  val content: String? = null,
  val contentType: String? = "text/plain",
  @Id @GeneratedValue
  val id: Long = 0
)
