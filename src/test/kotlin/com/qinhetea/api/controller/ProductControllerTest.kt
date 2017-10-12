package com.qinhetea.api.controller

import com.qinhetea.api.entity.Product
import com.qinhetea.api.repository.RepositoriesInitializer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

  @Autowired
  private lateinit var mvc: MockMvc

  @Autowired
  private lateinit var initializer: RepositoriesInitializer

  private val products: List<Product> get() = initializer.products

  private val path = "/api/products"

  @Before
  fun initialize() {
    initializer.initialize()
  }

  @Test
  fun findAll() {
    mvc.perform(get(path)).
      andExpect(status().isOk).
      andExpect(jsonPath("_embedded.products").isArray).
      andExpect(jsonPath("_embedded.products[*].name").value(products.map { it.name })).
      andExpect(jsonPath("_embedded.products[*].detail.content").doesNotExist()).
      andExpect(jsonPath("_embedded.products[*].category.name").doesNotExist()).
      andExpect(jsonPath("_embedded.products[*].shop.name").doesNotExist()).
      andExpect(jsonPath("page.number").value(0)).
      andExpect(jsonPath("page.size").value(20)).
      andExpect(jsonPath("page.totalElements").value(products.size)).
      andExpect(jsonPath("page.totalPages").value(1))
  }

  @Test
  fun findAllWithInlineProjection() {
    val request = get(path).
      param("projection", "inline")
    mvc.perform(request).
      andExpect(status().isOk).
      andExpect(jsonPath("_embedded.products").isArray).
      andExpect(jsonPath("_embedded.products[*].name").value(products.map { it.name })).
      andExpect(jsonPath("_embedded.products[*].detail.content").doesNotExist()).
      andExpect(jsonPath("_embedded.products[*].category.name").value(products.map { it.category?.name })).
      andExpect(jsonPath("_embedded.products[*].shop.name").value(products.map { it.shop?.name })).
      andExpect(jsonPath("page.number").value(0)).
      andExpect(jsonPath("page.size").value(20)).
      andExpect(jsonPath("page.totalElements").value(products.size)).
      andExpect(jsonPath("page.totalPages").value(1))
  }

  @Test
  fun findAllWithInlineDetailProjection() {
    val request = get(path).
      param("projection", "inline-detail")
    mvc.perform(request).
      andExpect(status().isOk).
      andExpect(jsonPath("_embedded.products").isArray).
      andExpect(jsonPath("_embedded.products[*].name").value(products.map { it.name })).
      andExpect(jsonPath("_embedded.products[*].detail.content").value(products.map { it.detail?.content })).
      andExpect(jsonPath("_embedded.products[*].category.name").value(products.map { it.category?.name })).
      andExpect(jsonPath("_embedded.products[*].shop.name").value(products.map { it.shop?.name })).
      andExpect(jsonPath("page.number").value(0)).
      andExpect(jsonPath("page.size").value(20)).
      andExpect(jsonPath("page.totalElements").value(products.size)).
      andExpect(jsonPath("page.totalPages").value(1))
  }

}
