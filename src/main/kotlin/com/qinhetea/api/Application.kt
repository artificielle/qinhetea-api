package com.qinhetea.api

import com.qinhetea.api.entity.User
import com.qinhetea.api.repository.UserRepository
import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

  @Bean
  fun initializeUserRepository(
    userRepository: UserRepository
  ) = CommandLineRunner {
    if (userRepository.count() == 0L) {
      userRepository.saveAll(listOf(
        User(username = "admin", password = "pass", roles = arrayOf(User.Role.ADMIN)),
        User(username = "user", password = "pass", roles = arrayOf(User.Role.USER)),
        User(username = "actuator", password = "pass", roles = arrayOf(User.Role.ACTUATOR))
      ).map { it.encodePassword() })
    }
    logger.debug { "number of users = ${userRepository.count()}" }
  }

}

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
  SpringApplication.run(Application::class.java, *args)
}
