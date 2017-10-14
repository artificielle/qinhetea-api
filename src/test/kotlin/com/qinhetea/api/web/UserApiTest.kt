package com.qinhetea.api.web

import com.qinhetea.api.entity.User
import com.qinhetea.api.repository.RepositoriesInitializer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class UserApiTest {

  @Autowired
  private lateinit var mvc: MockMvc

  @Autowired
  private lateinit var initializer: RepositoriesInitializer

  private val users: List<User> get() = initializer.users

  private val path = "/api/users"

  @Before
  fun initialize() {
    initializer.initialize()
  }

  @Test
  @WithMockUser(roles = arrayOf(User.Role.ADMIN))
  fun findAll() {
    mvc.perform(get(path)).
      andExpect(status().isOk).
      andExpect(jsonPath("_embedded.users").isArray).
      andExpect(jsonPath("page.number").value(0)).
      andExpect(jsonPath("page.size").value(20)).
      andExpect(jsonPath("page.totalElements").value(users.size)).
      andExpect(jsonPath("page.totalPages").value(1))
  }

}
