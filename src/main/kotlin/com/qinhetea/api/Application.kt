package com.qinhetea.api

import com.qinhetea.api.repository.RepositoriesInitializer
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile

@SpringBootApplication
class Application {

  @Bean
  @Profile("development")
  fun initializeRepositories(initializer: RepositoriesInitializer) =
    CommandLineRunner { initializer.initialize() }

}

fun main(args: Array<String>) {
  SpringApplication.run(Application::class.java, *args)
}
