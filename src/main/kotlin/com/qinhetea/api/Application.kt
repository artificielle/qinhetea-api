package com.qinhetea.api

import com.qinhetea.api.entity.Item
import com.qinhetea.api.entity.ItemTag
import com.qinhetea.api.entity.User
import com.qinhetea.api.repository.ItemRepository
import com.qinhetea.api.repository.ItemTagRepository
import com.qinhetea.api.repository.UserRepository
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
    val itemA = itemRepository.save(Item(name = "item a"))
    val itemB = itemRepository.save(Item(name = "item b"))
    itemTagRepository.save(ItemTag(content = "tag a a", item = itemA))
    itemTagRepository.save(ItemTag(content = "tag a b", item = itemA))
    itemTagRepository.save(ItemTag(content = "tag b a", item = itemB))
    logger.debug { "itemRepository.count() = ${itemRepository.count()}" }
    logger.debug { "itemTagRepository.count() = ${itemTagRepository.count()}" }
    val itemAX = itemRepository.findById(itemA.id).get().let { item ->
      item.copy(tags = item.tags.map { it.copy(item = Item()) }.toMutableList())
    }
    logger.debug { "itemAX = $itemAX" }
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

}

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
  SpringApplication.run(Application::class.java, *args)
}
