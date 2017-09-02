package com.qinhetea.api

import com.qinhetea.api.entity.*
import com.qinhetea.api.repository.*
import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

  @Bean
  fun initializeItemRepository(
    itemRepository: ItemRepository,
    itemTagRepository: ItemTagRepository
  ) = CommandLineRunner {
    if (itemRepository.count() == 0L && itemTagRepository.count() == 0L) {
      val itemA = itemRepository.save(Item(name = "item a"))
      val itemB = itemRepository.save(Item(name = "item b"))
      itemTagRepository.save(ItemTag(content = "tag a a", item = itemA))
      itemTagRepository.save(ItemTag(content = "tag a b", item = itemA))
      itemTagRepository.save(ItemTag(content = "tag b a", item = itemB))
      val itemAX = itemRepository.findById(itemA.id).get().let { item ->
        item.copy(tags = item.tags.map { it.copy(item = Item()) }.toMutableList())
      }
      logger.debug { "itemAX = $itemAX" }
    }
  }

  @Bean
  fun initializeUserRepository(
    userRepository: UserRepository
  ) = CommandLineRunner {
    if (userRepository.count() == 0L) {
      userRepository.saveAll(listOf(
        User(username = "aqua", password = "pass", roles = arrayOf("ADMIN")),
        User(username = "user", password = "pass", roles = arrayOf("USER")),
        User(username = "actuator", password = "pass", roles = arrayOf("ACTUATOR"))
      ).map { it.encodePassword() })
    }
    logger.debug { "userRepository.count() = ${userRepository.count()}" }
  }

  @Bean
  fun initializeProductRepository(
    productRepository: ProductRepository,
    categoryRepository: CategoryRepository
  ) = CommandLineRunner {
    if (productRepository.count() == 0L && categoryRepository.count() == 0L) {
      val cat = categoryRepository.save(Category(name = "cat"))
      val tom = productRepository.save(Product(name = "tom", category = cat))
      logger.debug { "tom = $tom" }
    }
  }

}

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
  SpringApplication.run(Application::class.java, *args)
}
