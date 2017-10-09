package com.qinhetea.api.controller

import com.qinhetea.api.entity.Category
import com.qinhetea.api.entity.User
import com.qinhetea.api.repository.CategoryRepository
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("/api/categories")
class CategoryController(val repository: CategoryRepository) {

  @GetMapping("/")
  fun findAll(pagination: Pageable, category: Category): Page<Category> =
    repository.findAll(
      Example.of(
        category,
        ExampleMatcher.matching().
          withIgnoreNullValues().
          withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
      ),
      pagination
    )

  @PostMapping("/save")
  @RolesAllowed(User.Role.ADMIN)
  fun save(category: Category): Category =
    repository.save(category)

  @PostMapping("/delete/{id}")
  @RolesAllowed(User.Role.ADMIN)
  fun delete(@PathVariable id: Long): Boolean {
    repository.deleteById(id)
    return !repository.findById(id).isPresent
  }

}
