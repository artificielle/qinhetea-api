package com.qinhetea.api.controller

import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Page
import org.springframework.hateoas.PagedResources
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources

fun <T> T.forExample(matcher: ExampleMatcher = defaultExampleMatcher): Example<T> =
  Example.of(this, matcher)

fun <T> Page<T>.asResources(): Resources<Resource<T>> =
  PagedResources.wrap(this, resourcesMetadata())

private fun <T> Page<T>.resourcesMetadata(): PagedResources.PageMetadata =
  PagedResources.PageMetadata(
    size.toLong(),
    number.toLong(),
    totalElements,
    totalPages.toLong()
  )

val defaultExampleMatcher: ExampleMatcher =
  ExampleMatcher.matching().
    withIgnoreCase().
    withIgnorePaths("id").
    withIgnoreNullValues().
    withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
