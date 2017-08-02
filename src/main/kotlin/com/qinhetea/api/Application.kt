package com.qinhetea.api

import com.qinhetea.api.entity.Item
import com.qinhetea.api.entity.ItemTag
import com.qinhetea.api.repository.ItemRepository
import com.qinhetea.api.repository.ItemTagRepository
import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

  @Bean
  fun initialize(
    itemRepository: ItemRepository,
    itemTagRepository: ItemTagRepository
  ) = CommandLineRunner {
    val itemA = itemRepository.save(Item(name = "item a"))
    val itemB = itemRepository.save(Item(name = "item b"))
    itemTagRepository.save(ItemTag(content = "tag a a", item = itemA))
    itemTagRepository.save(ItemTag(content = "tag a b", item = itemA))
    itemTagRepository.save(ItemTag(content = "tag b a", item = itemB))
    logger.info { "itemRepository.count() = ${itemRepository.count()}" }
    logger.info { "itemTagRepository.count() = ${itemTagRepository.count()}" }
    val itemAX = itemRepository.findById(itemA.id).get().let { item ->
      item.copy(tags = item.tags.map { tag ->
        tag.copy(item = Item())
      }.toMutableList())
    }
    logger.info { "itemAX = $itemAX" }
  }

}

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
  SpringApplication.run(Application::class.java, *args)
}
