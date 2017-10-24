package com.qinhetea.api.web

import com.qinhetea.api.repository.RepositoriesInitializer
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Ignore
@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationApiTest {

  @Autowired
  private lateinit var mvc: MockMvc

  @Autowired
  private lateinit var initializer: RepositoriesInitializer

  private val path = ""

  @Before
  fun initialize() {
    initializer.initialize()
  }

  @Test
  fun login() {
    val request = login(username = "user", password = "pass")
    mvc.perform(request).
      andExpect(status().isOk).
      andExpect(jsonPath("username").value("user"))
  }

  @Test
  fun loginWithIncorrectPassword() {
    val request = login(username = "user", password = "incorrect")
    mvc.perform(request).
      andExpect(status().isUnauthorized).
      andExpect(jsonPath("status").isArray).
      andExpect(jsonPath("error").value("Unauthorized")).
      andExpect(jsonPath("message").value("Bad credentials"))
  }

  @Test
  fun loginWithNonexistentUsername() {
    val request = login(username = "nonexistent", password = "pass")
    mvc.perform(request).
      andExpect(status().isUnauthorized).
      andExpect(jsonPath("status").isArray).
      andExpect(jsonPath("error").value("Unauthorized")).
      andExpect(jsonPath("message").value("Bad credentials"))
  }

  @Test
  fun unauthorized() {
    // TODO
  }

  private fun login(username: String, password: String) =
    post("$path/login").
      contentType(MediaType.APPLICATION_FORM_URLENCODED).
      content(
        listOf("username" to username, "password" to password).
          joinToString("&") { (key, value) -> "$key=$value" }
      )

}
