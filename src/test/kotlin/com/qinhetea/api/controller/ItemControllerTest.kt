package com.qinhetea.api.controller

import com.qinhetea.api.entity.Item
import com.qinhetea.api.repository.ItemRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

  @Autowired
  private lateinit var mvc: MockMvc

  @Autowired
  private lateinit var itemRepository: ItemRepository

  private lateinit var items: List<Item>

  private val path = "/api/items"

  @Before
  fun initialize() {
    assertEquals(itemRepository.count(), 0)

    items = itemRepository.saveAll(listOf(
      Item(name = "item-1"),
      Item(name = "item-2")
    ))

    assertEquals(itemRepository.count(), items.size.toLong())
  }

  @After
  fun cleanup() {
    itemRepository.deleteInBatch(items)
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
      andExpect(jsonPath("name").value(items.first().name))
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

}
