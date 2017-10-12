package com.qinhetea.api.entity.projection

import com.qinhetea.api.entity.Product
import com.qinhetea.api.entity.ProductDetail
import org.springframework.data.rest.core.config.Projection

@Projection(name = "inline-detail", types = arrayOf(Product::class))
interface ProductInlineDetailProjection : ProductInlineProjection {
  val detail: ProductDetail?
}
