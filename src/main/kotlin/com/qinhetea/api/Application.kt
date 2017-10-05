package com.qinhetea.api

import com.qinhetea.api.entity.*
import com.qinhetea.api.repository.*
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration

@SpringBootApplication
@EnableAutoConfiguration(exclude = arrayOf(RepositoryRestMvcAutoConfiguration::class))
class Application {

  @Bean
  fun initializeUserRepository(
    userRepository: UserRepository
  ) = CommandLineRunner {
    if (userRepository.count() == 0L) {
      userRepository.saveAll(listOf(
        User(username = "admin", password = "123", roles = arrayOf("ADMIN"), id = null),
        User(username = "user", password = "123", roles = arrayOf("USER"), id = null),
        User(username = "actuator", password = "123", roles = arrayOf("ACTUATOR"), id = null)
      ).map { it.encodePassword() })
    }
  }
}

fun main(args: Array<String>) {
  SpringApplication.run(Application::class.java, *args)
}
