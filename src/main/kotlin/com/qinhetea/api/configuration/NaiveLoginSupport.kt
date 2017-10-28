package com.qinhetea.api.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.qinhetea.api.repository.UserRepository
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Component

@Component
@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class NaiveLoginSupport {

  fun naiveLogin(http: HttpSecurity): AbstractHttpConfigurer<*, HttpSecurity> =
    http.
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
