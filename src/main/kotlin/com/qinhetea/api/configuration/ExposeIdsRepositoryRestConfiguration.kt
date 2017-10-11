package com.qinhetea.api.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter
import javax.persistence.Entity
import javax.persistence.EntityManagerFactory

@Configuration
class ExposeIdsRepositoryRestConfiguration : RepositoryRestConfigurerAdapter() {

  override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration) {
    config.exposeIdsFor(*allManagedEntityClasses())
  }

  private fun allManagedEntityClasses() =
    entityManagerFactory.metamodel.managedTypes.
      map { it.javaType }.
      filter { it.isAnnotationPresent(Entity::class.java) }.
      toTypedArray()

  @Autowired
  private lateinit var entityManagerFactory: EntityManagerFactory

}
