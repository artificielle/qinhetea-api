package com.qinhetea.api.repository

import com.qinhetea.api.entity.*
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service

@Service
class RepositoriesInitializer {

  @Autowired
  private lateinit var userRepository: UserRepository

  @Autowired
  private lateinit var categoryRepository: CategoryRepository

  @Autowired
  private lateinit var shopRepository: ShopRepository

  @Autowired
  private lateinit var productRepository: ProductRepository

  @Autowired
  private lateinit var productDetailRepository: ProductDetailRepository

  @Autowired
  private lateinit var itemRepository: ItemRepository

  lateinit var users: List<User>

  lateinit var categories: List<Category>

  lateinit var shops: List<Shop>

  lateinit var products: List<Product>

  lateinit var items: List<Item>

  private val pagination = PageRequest.of(0, 10)

  private val lock = Any()

  @Volatile
  private var initialized = false

  fun initialize() {
    if (!initialized) {
      synchronized(lock, {
        if (!initialized) {
          initializeAll()
          initialized = true
        }
      })
    }
  }

  private fun initializeAll() {
    initializeUsers()
    initializeCategories()
    initializeShops()
    initializeProducts()
    initializeItems()
  }

  private fun initializeUsers(): List<User> {
    assertEmpty(userRepository)

    users = userRepository.saveAll(listOf(
      User(
        username = "admin",
        password = "pass",
        roles = arrayOf(User.Role.ADMIN)
      ),
      User(
        username = "user",
        password = "pass",
        roles = arrayOf(User.Role.USER)
      ),
      User(
        username = "actuator",
        password = "pass",
        roles = arrayOf(User.Role.ACTUATOR)
      )
    ).map { it.encodePassword() })

    logger.debug {
      "initialized users = ${userRepository.findAll(pagination)}"
    }

    return users
  }

  private fun initializeCategories(): List<Category> {
    assertEmpty(categoryRepository)

    categories = categoryRepository.saveAll(listOf(
      Category(name = "category-1")
    ))

    logger.debug {
      "initialized categories = ${categoryRepository.findAll(pagination)}"
    }

    return categories
  }

  private fun initializeShops(): List<Shop> {
    assertEmpty(shopRepository)

    shops = shopRepository.saveAll(listOf(
      Shop(name = "shop-1")
    ))

    logger.debug {
      "initialized shops = ${shopRepository.findAll(pagination)}"
    }

    return shops
  }

  private fun initializeProducts(): List<Product> {
    assertEmpty(productRepository)
    assertEmpty(productDetailRepository)
    assertNonEmpty(categoryRepository)
    assertNonEmpty(shopRepository)

    products = productRepository.saveAll(listOf(
      Product(
        name = "product-1",
        detail = ProductDetail(content = "product-detail-1"),
        category = categories.first(),
        shop = shops.first()
      ),
      Product(
        name = "product-2",
        detail = ProductDetail(content = "product-detail-2"),
        category = categories.first(),
        shop = shops.first()
      )
    ))

    logger.debug {
      "initialized products = ${productRepository.findAll(pagination)}"
    }

    return products
  }

  private fun initializeItems(): List<Item> {
    assertEmpty(itemRepository)

    items = itemRepository.saveAll(listOf(
      Item(name = "item-1"),
      Item(name = "item-2")
    ))

    logger.debug {
      "initialized items = ${itemRepository.findAll(pagination)}"
    }

    return items
  }

  private fun assertEmpty(repository: CrudRepository<*, *>) {
    assertCount(repository, 0L)
  }

  private fun assertNonEmpty(repository: CrudRepository<*, *>) {
    assertCountGTE(repository, 1L)
  }

  private fun assertCount(repository: CrudRepository<*, *>, count: Long) {
    assertTrue(repository.count() == count)
  }

  private fun assertCountGTE(repository: CrudRepository<*, *>, count: Long) {
    assertTrue(repository.count() >= count)
  }

  private fun assertTrue(condition: Boolean) {
    if (!condition) throw AssertionError()
  }

}

private val logger = KotlinLogging.logger {}
