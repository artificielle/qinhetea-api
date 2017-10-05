package com.qinhetea.api.controller

import com.qinhetea.api.entity.Product
import com.qinhetea.api.entity.ProductDetail
import com.qinhetea.api.repository.ProductDetailRepository
import com.qinhetea.api.repository.ProductRepository
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("/api/products")
class ProductController(val repository: ProductRepository) {

  @GetMapping("")
  fun findAll(pageable: Pageable, product: Product): Page<Product> =
    repository.findAll(
      Example.of(product,
        ExampleMatcher.matching()
          .withIgnoreCase()
          .withIgnorePaths("img")
          .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
          .withIgnoreNullValues()), pageable)

  @PostMapping("/save")
  @RolesAllowed("ADMIN")
  @Transactional
  fun save(product: Product, detail: ProductDetail): Product =
    repository.save(product.copy(detail = detail))

  @PostMapping("/del/{id}")
  @RolesAllowed("ADMIN")
  @Transactional
  fun del(@PathVariable id: Long): Boolean {
    repository.delete(Product(id = id))
    val product: Product? = repository.findById(id).orElse(null)
    return product === null
  }
}
