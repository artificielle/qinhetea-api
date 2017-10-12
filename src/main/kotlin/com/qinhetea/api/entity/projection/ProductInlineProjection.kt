package com.qinhetea.api.entity.projection

import com.qinhetea.api.entity.Category
import com.qinhetea.api.entity.Product
import com.qinhetea.api.entity.Shop
import org.springframework.data.rest.core.config.Projection

/**
 * @see <a href="https://docs.spring.io/spring-data/rest/docs/current/reference/html/#projections-excerpts">projections</a>
 */
@Projection(name = "inline", types = arrayOf(Product::class))
interface ProductInlineProjection {
  val name: String?
  val subtitle: String?
  val img: String?
  val category: Category?
  val shop: Shop?
  val id: Long?
}
