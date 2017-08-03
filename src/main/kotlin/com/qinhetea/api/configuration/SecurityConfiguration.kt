package com.qinhetea.api.configuration

import com.qinhetea.api.entity.User
import com.qinhetea.api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Configuration
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    http.
      authorizeRequests().anyRequest().authenticated().and().
      formLogin().and().
      // TODO
      csrf().disable().
      httpBasic()
  }

  override fun configure(auth: AuthenticationManagerBuilder) {
    auth.userDetailsService(userDetailsService()).passwordEncoder(User.encoder)
  }

  @Autowired
  private lateinit var userRepository: UserRepository

  @Bean
  override fun userDetailsService() = UserDetailsService { username ->
    userRepository.findByUsername(username).
      map { it.toUserDetails() }.
      orElseThrow { UsernameNotFoundException(username) }
  }

}
