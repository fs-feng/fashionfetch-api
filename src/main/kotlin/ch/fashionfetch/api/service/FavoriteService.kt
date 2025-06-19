package ch.fashionfetch.api.service

import ch.fashionfetch.api.model.ProductEntity
import ch.fashionfetch.api.repository.ProductRepository
import ch.fashionfetch.api.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FavoriteService(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) {

    @Transactional
    fun addFavorite(userId: Long, productId: Long): Boolean {
        val user = userRepository.findById(userId).orElse(null) ?: return false
        val product = productRepository.findById(productId).orElse(null) ?: return false

        if (product !in user.favorites) {
            user.favorites.add(product)
            userRepository.save(user)
        }
        return true
    }

    @Transactional
    fun removeFavorite(userId: Long, productId: Long): Boolean {
        val user = userRepository.findById(userId).orElse(null) ?: return false
        val product = productRepository.findById(productId).orElse(null) ?: return false

        if (product in user.favorites) {
            user.favorites.remove(product)
            userRepository.save(user)
        }
        return true
    }

    fun getFavorites(userId: Long): List<ProductEntity> {
        val user = userRepository.findById(userId).orElse(null) ?: return emptyList()
        return user.favorites.toList()
    }

    fun isFavorite(userId: Long, productId: Long): Boolean {
        val user = userRepository.findById(userId).orElse(null) ?: return false
        return user.favorites.any { it.id == productId }
    }
}