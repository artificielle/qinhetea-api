package com.qinhetea.api.web

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.qinhetea.api.entity.Item
import com.qinhetea.api.repository.RepositoriesInitializer
import org.junit.Before
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ItemApiTest {

  @Autowired
  private lateinit var mvc: MockMvc

  @Autowired
  private lateinit var initializer: RepositoriesInitializer

  private val items: List<Item> get() = initializer.items

  private val path = "/api/items"

  @Before
  fun initialize() {
    initializer.initialize()
  }

  @Test
  fun findAll() {
    mvc.perform(get(path)).
      andExpect(status().isOk).
      andExpect(jsonPath("_embedded.items").isArray).
      andExpect(jsonPath("_embedded.items[*].name").value(items.map { it.name })).
      andExpect(jsonPath("page.number").value(0)).
      andExpect(jsonPath("page.size").value(20)).
      andExpect(jsonPath("page.totalElements").value(items.size)).
      andExpect(jsonPath("page.totalPages").value(1))
  }

  @Test
  fun findAllWithPagination() {
    val request = get(path).
      param("sort", "name,desc").
      param("page", "0").
      param("size", "10")
    mvc.perform(request).
      andExpect(status().isOk).
      andExpect(jsonPath("_embedded.items").isArray).
      andExpect(jsonPath("_embedded.items[*].name").value(items.map { it.name }.reversed())).
      andExpect(jsonPath("page.number").value(0)).
      andExpect(jsonPath("page.size").value(10)).
      andExpect(jsonPath("page.totalElements").value(items.size)).
      andExpect(jsonPath("page.totalPages").value(1))
  }

  @Test
  fun findOneById() {
    mvc.perform(get("$path/${items.first().id}")).
      andExpect(status().isOk).
      andExpect(jsonPath("name").value(items.first().name!!))
  }

  @Test
  fun findAllByNameContainingIgnoreCase() {
    val request = get("$path/search/findByNameContainingIgnoreCase").
      param("name", "TE")
    mvc.perform(request).
      andExpect(status().isOk).
      andExpect(jsonPath("_embedded.items").isArray).
      andExpect(jsonPath("_embedded.items[*].name").value(items.map { it.name })).
      andExpect(jsonPath("page.number").value(0)).
      andExpect(jsonPath("page.size").value(20)).
      andExpect(jsonPath("page.totalElements").value(items.size)).
      andExpect(jsonPath("page.totalPages").value(1))
  }

  @Test
  @WithMockUser
  fun fuzzySearch() {
    val request = post("$path/search").
      contentType(MediaType.APPLICATION_JSON).
      content(jacksonObjectMapper().writeValueAsString(Item(
        name = "TE"
      )))
    mvc.perform(request).
      andExpect(status().isOk).
      andExpect(jsonPath("_embedded.items").isArray).
      andExpect(jsonPath("_embedded.items[*].name").value(items.map { it.name })).
      andExpect(jsonPath("page.number").value(0)).
      andExpect(jsonPath("page.size").value(20)).
      andExpect(jsonPath("page.totalElements").value(items.size)).
      andExpect(jsonPath("page.totalPages").value(1))
  }

}
