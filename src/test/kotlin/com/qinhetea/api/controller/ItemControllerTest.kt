package com.qinhetea.api.controller

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

  @Autowired
  private lateinit var mvc: MockMvc

  @Test
  fun findAll() {
    mvc.perform(get("/api/items/").accept(MediaType.APPLICATION_JSON)).
      andExpect(status().isOk).
      andExpect(content().json("""
        [
          {
            "name": "jacky mao",
            "content": "",
            "id": 1
          },
          {
            "name": "eternalenvy",
            "content": "",
            "id": 2
          }
        ]
      """))
  }

}
