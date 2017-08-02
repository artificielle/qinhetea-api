package com.qinhetea.api.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class SecurityConfigurer : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    super.configure(http)
  }

  @Autowired
  fun configureGlobal(builder: AuthenticationManagerBuilder) {
    builder.inMemoryAuthentication().
      withUser("user").password("123").roles("USER")
  }

}
