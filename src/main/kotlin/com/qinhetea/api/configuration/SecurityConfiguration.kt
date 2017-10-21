package com.qinhetea.api.configuration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.qinhetea.api.entity.User
import com.qinhetea.api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    http.
      authorizeRequests().
      mvcMatchers("/api/users/**").hasRole(User.Role.ADMIN).
      mvcMatchers(HttpMethod.GET, "/api/**").permitAll().
      mvcMatchers("/api/**").authenticated().
      anyRequest().permitAll().and().
      formLogin().
      successHandler { request, response, authentication ->
        val user = userRepository.findByUsername(authentication.name).get()
        response.characterEncoding = "UTF-8"
        response.writer.write(jacksonObjectMapper().writeValueAsString(user))
        response.setHeader("Content-Type", "application/json")
        response.status = 200
      }.and().
      csrf().disable().
      httpBasic()
  }

  override fun configure(auth: AuthenticationManagerBuilder) {
    auth.userDetailsService(userDetailsService()).passwordEncoder(User.encoder)
  }

  @Bean
  override fun userDetailsService() = UserDetailsService { username ->
    userRepository.findByUsername(username).
      map { it.toUserDetails() }.
      orElseThrow { UsernameNotFoundException(username) }
  }

  @Autowired
  private lateinit var userRepository: UserRepository

}
