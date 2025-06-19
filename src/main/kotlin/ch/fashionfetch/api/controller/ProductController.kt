package ch.fashionfetch.api.controller

import ch.fashionfetch.api.model.ProductEntity
import ch.fashionfetch.api.repository.ProductRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productRepository: ProductRepository
) {

    @GetMapping
    fun getAllProducts(): List<ProductEntity> {
        return productRepository.findAll()
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ProductEntity? {
        return productRepository.findById(id).orElse(null)
    }

    @GetMapping("/search")
    fun searchByTitle(@RequestParam title: String): List<ProductEntity> {
        return productRepository.findByTitleContainingIgnoreCase(title)
    }
}