package com.qinhetea.api.configuration

import mu.KotlinLogging
import org.springframework.core.annotation.Order
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Order
@WebFilter
class WebLoggingFilter : Filter {

  override fun doFilter(
    request: ServletRequest,
    response: ServletResponse,
    chain: FilterChain
  ) {
    if (request is HttpServletRequest && response is HttpServletResponse) {
      log(request, response)
    }
    chain.doFilter(request, response)
  }

  @Suppress("UNUSED_PARAMETER")
  private fun log(request: HttpServletRequest, response: HttpServletResponse) {
    logger.info { "${request.method} ${request.requestURL} - ${request.userPrincipal?.name ?: "<anonymous>"}" }
  }

  override fun init(config: FilterConfig) = Unit

  override fun destroy() = Unit

}

private val logger = KotlinLogging.logger {}
