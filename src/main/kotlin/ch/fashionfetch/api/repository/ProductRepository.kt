package ch.fashionfetch.api.repository

import ch.fashionfetch.api.model.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductEntity, Long>{
    fun existsByUrl(url: String): Boolean
    fun findByTitleContainingIgnoreCase(title: String): List<ProductEntity>
}