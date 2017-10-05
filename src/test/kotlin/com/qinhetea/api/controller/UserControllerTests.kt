package com.qinhetea.api.controller

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
class UserControllerTests {

  @Autowired
  private lateinit var mvc: MockMvc

  @Test
  @WithMockUser(roles = arrayOf("ADMIN"))
  fun findAllTest() {
    mvc.perform(get("/api/users").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk)
      .andExpect(jsonPath("content").isArray)
      .andExpect(jsonPath("number").value(0))
      .andExpect(jsonPath("size").value(20))
  }

  @Test
  @WithMockUser(roles = arrayOf("ADMIN"))
  fun findByUser() {
    mvc.perform(
        get("/api/users")
          .accept(MediaType.APPLICATION_JSON)
          .param("id", "0")
          .param("name", "q")
          .param("subtitle", "q")
          .param("shop", "1")
          .param("category", "2")
          .param("detail", "3"))
      .andExpect(status().isOk)
      .andExpect(jsonPath("content").isArray)
      .andExpect(jsonPath("number").value(0))
      .andExpect(jsonPath("size").value(20))
  }

  @Test
  @WithMockUser(roles = arrayOf("ADMIN"))
  fun findByPagingAndSorting() {
    mvc.perform(
      get("/api/users")
        .accept(MediaType.APPLICATION_JSON)
        .param("sort", "name,desc")
        .param("page", "0")
        .param("size", "10"))
      .andExpect(status().isOk)
      .andExpect(jsonPath("content").isArray)
      .andExpect(jsonPath("number").value(0))
      .andExpect(jsonPath("size").value(10))
  }

}
