package com.qinhetea.api.controller

import com.qinhetea.api.entity.Product
import com.qinhetea.api.entity.ProductDetail
import com.qinhetea.api.entity.User
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

  @GetMapping("/")
  fun findAll(pagination: Pageable, product: Product): Page<Product> =
    repository.findAll(
      Example.of(
        product,
        ExampleMatcher.matching().
          withIgnoreCase().
          withIgnorePaths("img").
          withIgnoreNullValues().
          withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
      ),
      pagination
    )

  @PostMapping("/save")
  @RolesAllowed(User.Role.ADMIN)
  @Transactional
  fun save(product: Product, detail: ProductDetail): Product =
    repository.save(product.copy(detail = detail))

  @PostMapping("/delete/{id}")
  @RolesAllowed(User.Role.ADMIN)
  @Transactional
  fun delete(@PathVariable id: Long): Boolean {
    repository.deleteById(id)
    return !repository.findById(id).isPresent
  }

}
