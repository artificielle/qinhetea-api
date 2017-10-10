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
import org.junit.Ignore
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

@Ignore
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

  private lateinit var mockProducts: List<Product>

  private lateinit var mockProductDetails: List<ProductDetail>

  private lateinit var mockCategories: List<Category>

  private lateinit var mockShops: List<Shop>

  private val path = "/api/products"

  @Before
  fun initialize() {
    assertEquals(productRepository.count(), 0)
    assertEquals(productDetailRepository.count(), 0)
    assertEquals(categoryRepository.count(), 0)
    assertEquals(shopRepository.count(), 0)

    mockShops = listOf(
      Shop(name = "shop-1")
    ).let { shopRepository.saveAll(it) }

    mockCategories = listOf(
      Category(name = "category-1")
    ).let { categoryRepository.saveAll(it) }

    mockProductDetails = listOf(
      ProductDetail(content = "product-detail-1"),
      ProductDetail(content = "product-detail-2")
    ).let { productDetailRepository.saveAll(it) }

    mockProducts = listOf(
      Product(
        name = "product-1",
        detail = mockProductDetails[0],
        category = mockCategories.first(),
        shop = mockShops.first()
      ),
      Product(
        name = "product-2",
        detail = mockProductDetails[1],
        category = mockCategories.first(),
        shop = mockShops.first()
      )
    ).let { productRepository.saveAll(it) }
  }

  @After
  fun cleanup() {
    productRepository.deleteInBatch(mockProducts)
    productDetailRepository.deleteInBatch(mockProductDetails)
    categoryRepository.deleteInBatch(mockCategories)
    shopRepository.deleteInBatch(mockShops)
  }

  @Test
  fun findAll() {
    mvc.perform(get(path)).
      andExpect(status().isOk).
      andExpect(jsonPath("_embedded.products").isArray).
      andExpect(jsonPath("_embedded.products[*].name").value(mockProducts.map { it.name })).
      andExpect(jsonPath("_embedded.products[*].detail.content").value(mockProducts.map { it.detail?.content })).
      andExpect(jsonPath("page.number").value(0)).
      andExpect(jsonPath("page.size").value(20)).
      andExpect(jsonPath("page.totalElements").value(mockProducts.size)).
      andExpect(jsonPath("page.totalPages").value(1))
  }

}
