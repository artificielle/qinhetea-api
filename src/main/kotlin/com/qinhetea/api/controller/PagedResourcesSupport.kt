package com.qinhetea.api.controller

import org.springframework.data.domain.Page
import org.springframework.hateoas.PagedResources
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources

fun <T> Page<T>.asResources(): Resources<Resource<T>> =
  PagedResources.wrap(this, resourcesMetadata())

private fun <T> Page<T>.resourcesMetadata(): PagedResources.PageMetadata =
  PagedResources.PageMetadata(
    size.toLong(),
    number.toLong(),
    totalElements,
    totalPages.toLong()
  )
