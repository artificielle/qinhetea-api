package com.qinhetea.api.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.qinhetea.api.entity.Item
import org.hamcrest.Matchers.containsString
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

  @Autowired
  private lateinit var mvc: MockMvc

  @Test
  @WithMockUser
  fun findAll() {
    mvc.perform(get("/api/items")).
      andExpect(status().isOk).
      andExpect(content().json("""
        {
          "_embedded": {
            "items": [{
              "name": "item a",
              "_links": {
                "self": {
                  "href": "http://localhost/api/items/1"
                },
                "item": {
                  "href": "http://localhost/api/items/1"
                },
                "tags": {
                  "href": "http://localhost/api/items/1/tags"
                }
              }
            }, {
              "name": "item b",
              "_links": {
                "self": {
                  "href": "http://localhost/api/items/2"
                },
                "item": {
                  "href": "http://localhost/api/items/2"
                },
                "tags": {
                  "href": "http://localhost/api/items/2/tags"
                }
              }
            }]
          },
          "_links": {
            "self": {
              "href": "http://localhost/api/items{?page,size,sort}",
              "templated": true
            },
            "profile": {
              "href": "http://localhost/api/profile/items"
            },
            "search": {
              "href": "http://localhost/api/items/search"
            }
          },
          "page": {
            "size": 20,
            "totalElements": 2,
            "totalPages": 1,
            "number": 0
          }
        }
      """))
  }

  @Test
  @WithMockUser
  fun findById() {
    mvc.perform(get("/api/items/1")).
      andExpect(status().isOk).
      andExpect(content().json("""
        {
          "name": "item a",
          "_links": {
            "self": {
              "href": "http://localhost/api/items/1"
            },
            "item": {
              "href": "http://localhost/api/items/1"
            },
            "tags": {
              "href": "http://localhost/api/items/1/tags"
            }
          }
        }
      """))
  }

  @Test
  @WithMockUser
  fun findByName() {
    mvc.perform(get("/api/items/search/findByName?name=item a")).
      andExpect(status().isOk).
      andExpect(content().json("""
        {
          "name": "item a",
          "_links": {
            "self": {
              "href": "http://localhost/api/items/1"
            },
            "item": {
              "href": "http://localhost/api/items/1"
            },
            "tags": {
              "href": "http://localhost/api/items/1/tags"
            }
          }
        }
      """))
  }

  @Test
  @WithMockUser
  fun save() {
    val content = jacksonObjectMapper().writeValueAsString(
      Item(name = "xxx")
    )
    mvc.perform(post("/api/items").content(content)).
      andExpect(status().isCreated).
      andExpect(header().string("Location", containsString("/items/")))
  }

}
