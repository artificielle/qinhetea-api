package com.qinhetea.api.configuration

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
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class WebSecurityConfiguration : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    http.
      authorizeRequests().
      mvcMatchers("/api/users/**").hasRole(User.Role.ADMIN).
      mvcMatchers(HttpMethod.GET, "/api/**").permitAll().
      mvcMatchers("/api/**").authenticated().
      anyRequest().permitAll().and().
      naiveLogin().and().
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

  private fun HttpSecurity.naiveLogin() = naiveLoginSupport.naiveLogin(this)

  @Autowired
  private lateinit var userRepository: UserRepository

  @Autowired
  private lateinit var naiveLoginSupport: NaiveLoginSupport

}
