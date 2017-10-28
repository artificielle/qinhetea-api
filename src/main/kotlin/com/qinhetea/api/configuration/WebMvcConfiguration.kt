package com.qinhetea.api.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
@Profile("development")
class WebMvcConfiguration : WebMvcConfigurer {

  override fun addCorsMappings(registry: CorsRegistry) {
    registry.
      addMapping("/**").
      allowedOrigins("http://localhost:8000").
      allowedMethods("*").
      allowedHeaders("*").
      allowCredentials(true)
  }

}
