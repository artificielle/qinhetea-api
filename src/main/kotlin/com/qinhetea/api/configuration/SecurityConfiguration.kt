package com.qinhetea.api.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.qinhetea.api.entity.User
import com.qinhetea.api.repository.UserRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
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
      naiveAjaxLogin().and().
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

  private fun HttpSecurity.naiveAjaxLogin() =
    this.
      exceptionHandling().
      authenticationEntryPoint(authenticationEntryPoint).
      and().formLogin().
      failureHandler(authenticationFailureHandler).
      successHandler(authenticationSuccessHandler).
      and().logout().
      logoutSuccessHandler(logoutSuccessHandler)

  private val authenticationEntryPoint =
    AuthenticationEntryPoint { request, response, exception ->
      logger.trace { "Handling authentication exception: $exception" }
      response.sendError(
        HttpStatus.UNAUTHORIZED.value(),
        exception.message ?: HttpStatus.UNAUTHORIZED.reasonPhrase
      )
    }

  private val authenticationFailureHandler =
    AuthenticationFailureHandler { request, response, exception ->
      logger.trace { "Handling authentication failure: $exception" }
      response.sendError(
        HttpStatus.UNAUTHORIZED.value(),
        exception.message
      )
    }

  private val authenticationSuccessHandler =
    AuthenticationSuccessHandler { request, response, authentication ->
      logger.trace { "Handling authentication success: $authentication" }
      /** @see org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler */
      // request.getSession(false)?.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)
      response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
      mapper.writeValue(
        response.writer,
        userRepository.findByUsername(authentication.name).get()
      )
    }

  private val logoutSuccessHandler =
    LogoutSuccessHandler { request, response, authentication ->
      logger.trace { "Handling logout success: $authentication" }
    }

  @Autowired
  private lateinit var userRepository: UserRepository

  @Autowired
  private lateinit var mapper: ObjectMapper

}

private val logger = KotlinLogging.logger {}
