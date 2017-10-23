package com.qinhetea.api.controller

import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface FuzzySearchController<T> {

  val repository: JpaRepository<T, Long>

  @PostMapping("/search")
  fun search(@RequestBody x: T, pagination: Pageable) =
    ResponseEntity.ok(
      repository.findAll(x.forExample(), pagination).asResources()
    )

  fun <T> T.forExample(matcher: ExampleMatcher = defaultExampleMatcher): Example<T> =
    Example.of(this, matcher)

  val defaultExampleMatcher: ExampleMatcher
    get() = ExampleMatcher.matching().
      withIgnoreCase().
      withIgnorePaths("id").
      withIgnoreNullValues().
      withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)

}
