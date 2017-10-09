package com.qinhetea.api.controller

import com.qinhetea.api.entity.User
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired
  private lateinit var mvc: MockMvc

  @Test
  @WithMockUser(roles = arrayOf(User.Role.ADMIN))
  fun findAll() {
    val request = get("/api/users").
      accept(MediaType.APPLICATION_JSON)
    mvc.perform(request).
      andExpect(status().isOk).
      andExpect(jsonPath("$._embedded.users").isArray).
      andExpect(jsonPath("$.page.number").value(0)).
      andExpect(jsonPath("$.page.size").value(20)).
      andExpect(jsonPath("$.page.totalElements").value(3))
  }

  @Test
  @WithMockUser(roles = arrayOf(User.Role.ADMIN))
  fun findAllWithPagination() {
    val request = get("/api/users").
      accept(MediaType.APPLICATION_JSON).
      param("sort", "name,desc").
      param("page", "0").
      param("size", "10")
    mvc.perform(request).
      andExpect(status().isOk).
      andExpect(jsonPath("$._embedded.users").isArray).
      andExpect(jsonPath("$.page.number").value(0)).
      andExpect(jsonPath("$.page.size").value(10)).
      andExpect(jsonPath("$.page.totalElements").value(3))
  }

}
