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

  // @Bean
  // fun corsFilter(
  //   @Value("\${tagit.origin:http://localhost:23333}") origin: String
  // ) = CorsFilter {
  //   CorsConfiguration().
  //     also { it.allowedOrigins = listOf("*", origin) }.
  //     also { it.allowedMethods = HttpMethod.values().map { it.name } }.
  //     also { it.allowCredentials = true }.
  //     also { it.maxAge = 3600 }.
  //     also {
  //       it.allowedHeaders = listOf(
  //         "Origin", "Accept", "X-Requested-With", "Content-Type", "Authorization",
  //         "Access-Control-Request-Method", "Access-Control-Request-Headers"
  //       )
  //     }
  // }

  // @Bean
  // fun corsFilter(
  //   @Value("\${tagit.origin:http://localhost:23333}") origin: String
  // ): FilterRegistrationBean<Filter> = FilterRegistrationBean(object : Filter {
  //
  //   override fun doFilter(
  //     request: ServletRequest,
  //     response: ServletResponse,
  //     chain: FilterChain
  //   ) {
  //     doFilter(request as HttpServletRequest, response as HttpServletResponse, chain)
  //   }
  //
  //   fun doFilter(
  //     request: HttpServletRequest,
  //     response: HttpServletResponse,
  //     chain: FilterChain
  //   ) {
  //     response.setHeader("Access-Control-Allow-Origin", origin)
  //     response.setHeader("Access-Control-Allow-Methods",
  //       "POST, GET, OPTIONS, DELETE")
  //     response.setHeader("Access-Control-Max-Age", (60 * 60).toString())
  //     response.setHeader("Access-Control-Allow-Credentials", "true")
  //     response.setHeader(
  //       "Access-Control-Allow-Headers",
  //       "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization")
  //
  //     when (request.method) {
  //       "OPTIONS" -> response.status = HttpStatus.OK.value()
  //       else -> chain.doFilter(request, response)
  //     }
  //   }
  //
  //   override fun init(filterConfig: FilterConfig) {}
  //
  //   override fun destroy() {}
  //
  // })

  @Bean
  fun initializeUserRepository(
    userRepository: UserRepository
  ) = CommandLineRunner {
    if (userRepository.count() == 0L) {
      userRepository.saveAll(listOf(
        User(username = "aqua", password = "pass", role = "ADMIN"),
        User(username = "user", password = "pass", role = "USER"),
        User(username = "actuator", password = "pass", role = "ACTUATOR")
      ).map { it.encodePassword() })
    }
    logger.info { "userRepository.count() = ${userRepository.count()}" }
  }

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
    logger.info { "itemRepository.count() = ${itemRepository.count()}" }
    logger.info { "itemTagRepository.count() = ${itemTagRepository.count()}" }
    val itemAX = itemRepository.findById(itemA.id).get().let { item ->
      item.copy(tags = item.tags.map { it.copy(item = Item()) }.toMutableList())
    }
    logger.info { "itemAX = $itemAX" }
  }

}

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
  SpringApplication.run(Application::class.java, *args)
}
