package com.qinhetea.api.controller

import com.qinhetea.api.entity.Category
import com.qinhetea.api.entity.Product
import com.qinhetea.api.entity.ProductDetail
import com.qinhetea.api.entity.Shop
import com.qinhetea.api.repository.CategoryRepository
import com.qinhetea.api.repository.ProductDetailRepository
import com.qinhetea.api.repository.ProductRepository
import com.qinhetea.api.repository.ShopRepository
import org.junit.After
import org.junit.Assert.assertEquals
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
  private lateinit var productRepository: ProductRepository

  @Autowired
  private lateinit var productDetailRepository: ProductDetailRepository

  @Autowired
  private lateinit var categoryRepository: CategoryRepository

  @Autowired
  private lateinit var shopRepository: ShopRepository

  private lateinit var products: List<Product>

  private lateinit var categorie: Category

  private lateinit var shop: Shop

  private val path = "/api/products"

  @Before
  fun initialize() {
    assertEquals(productRepository.count(), 0)
    assertEquals(productDetailRepository.count(), 0)
    assertEquals(categoryRepository.count(), 0)
    assertEquals(shopRepository.count(), 0)

    shop = shopRepository.save(Shop(name = "shop-1"))

    categorie = categoryRepository.save(Category(name = "category-1"))

    products = productRepository.saveAll(listOf(
      Product(
        name = "product-1",
        detail = ProductDetail(content = "product-detail-1"),
        category = categorie,
        shop = shop
      ),
      Product(
        name = "product-2",
        detail = ProductDetail(content = "product-detail-2"),
        category = categorie,
        shop = shop
      )
    ))

    assertEquals(shopRepository.count(), 1)
    assertEquals(categoryRepository.count(), 1)
    assertEquals(productDetailRepository.count(), products.size.toLong())
    assertEquals(productRepository.count(), products.size.toLong())
  }

  @After
  fun cleanup() {
    productRepository.deleteInBatch(products)
    categoryRepository.delete(categorie)
    shopRepository.delete(shop)
  }

  @Test
  fun findAll() {
    mvc.perform(get(path)).
      andExpect(status().isOk).
      andExpect(jsonPath("_embedded.products").isArray).
      andExpect(jsonPath("_embedded.products[*].name").value(products.map { it.name })).
      // andExpect(jsonPath("_embedded.products[*].detail.content").value(products.map { it.detail?.content })).
      // andExpect(jsonPath("_embedded.products[*].category.name").value(products.map { it.category?.name })).
      // andExpect(jsonPath("_embedded.products[*].shop.name").value(products.map { it.shop?.name })).
      andExpect(jsonPath("page.number").value(0)).
      andExpect(jsonPath("page.size").value(20)).
      andExpect(jsonPath("page.totalElements").value(products.size)).
      andExpect(jsonPath("page.totalPages").value(1))
  }

}
