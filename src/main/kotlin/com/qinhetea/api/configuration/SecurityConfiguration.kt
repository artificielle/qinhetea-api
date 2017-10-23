package com.qinhetea.api.configuration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.qinhetea.api.entity.User
import com.qinhetea.api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    http.
      exceptionHandling().
      authenticationEntryPoint { _, response, authException -> authFailHandler(response, authException) }.
      and().
      authorizeRequests().
      mvcMatchers("/api/users/**").hasRole(User.Role.ADMIN).
      mvcMatchers(HttpMethod.GET, "/api/**").permitAll().
      mvcMatchers("/api/**").authenticated().
      anyRequest().permitAll().and().
      formLogin().
      failureHandler { _, response, exception -> authFailHandler(response, exception) }.
      successHandler { _, response, authentication -> loginSuccessHandler(response, authentication) }.
      and().
      logout().logoutSuccessHandler { _, response, _ -> logoutHandler(response) }.and().
      csrf().disable().
      httpBasic()
  }

  fun authFailHandler(response: HttpServletResponse, exception: Exception) {
    response.status = HttpServletResponse.SC_UNAUTHORIZED
    response.writer.write(exception.message.orEmpty())
  }

  fun loginSuccessHandler(response: HttpServletResponse, authentication: Authentication) {
    response.status = HttpServletResponse.SC_OK
    response.characterEncoding = "UTF-8"
    response.setHeader("Content-Type", "application/json")
    val user = userRepository.findByUsername(authentication.name).get()
    response.writer.write(jacksonObjectMapper().writeValueAsString(user))
  }

  fun logoutHandler(response: HttpServletResponse) {
    response.status = HttpServletResponse.SC_NO_CONTENT
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
