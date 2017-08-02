package com.qinhetea.api

import com.qinhetea.api.entity.Item
import com.qinhetea.api.repository.ItemRepository
import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

  @Bean
  fun initialize(repository: ItemRepository) = CommandLineRunner {
    repository.save(Item("jacky mao"))
    repository.save(Item("eternalenvy"))
    logger.info { "repository.count() = ${repository.count()}" }
  }

}

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
  SpringApplication.run(Application::class.java, *args)
}
