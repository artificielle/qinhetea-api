package com.qinhetea.api.web

import com.fasterxml.jackson.databind.JsonNode
import com.qinhetea.api.repository.RepositoriesInitializer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationApiTest {

  @Autowired
  private lateinit var template: TestRestTemplate

  @Autowired
  private lateinit var initializer: RepositoriesInitializer

  private val path = ""

  @Before
  fun initialize() {
    initializer.initialize()
  }

  @Test
  fun login() {
    val response = login(username = "user", password = "pass")
    assertEquals(HttpStatus.OK, response.statusCode)
    assertEquals("user", response.body!!.path("username").asText())
  }

  @Test
  fun loginWithIncorrectPassword() {
    val response = login(username = "user", password = "incorrect")
    assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
    assertEquals("Unauthorized", response.body!!.path("error").asText())
    assertEquals("Bad credentials", response.body!!.path("message").asText())
  }

  @Test
  fun loginWithNonexistentUsername() {
    val response = login(username = "nonexistent", password = "pass")
    assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
    assertEquals("Unauthorized", response.body!!.path("error").asText())
    assertEquals("Bad credentials", response.body!!.path("message").asText())
  }

  @Test
  fun unauthorized() {
    // TODO
  }

  private fun login(username: String, password: String) =
    template.postForEntity(
      "$path/login",
      HttpEntity(
        listOf("username" to username, "password" to password).
          joinToString("&") { (key, value) -> "$key=$value" },
        HttpHeaders().also {
          it.contentType = MediaType.APPLICATION_FORM_URLENCODED
        }
      ),
      JsonNode::class.java
    )

}
