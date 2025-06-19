package ch.fashionfetch.api.controller

import ch.fashionfetch.api.config.CustomUserDetails
import ch.fashionfetch.api.model.ProductEntity
import ch.fashionfetch.api.service.FavoriteService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/favorites")
class FavoriteController(
    private val favoriteService: FavoriteService
) {

    @PostMapping("/{productId}")
    fun addFavorite(
        @PathVariable productId: Long,
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): ResponseEntity<String> {
        return if (favoriteService.addFavorite(userDetails.user.id!!, productId))
            ResponseEntity.ok("Added to favorites.")
        else
            ResponseEntity.badRequest().body("User or product not found.")
    }

    @DeleteMapping("/{productId}")
    fun removeFavorite(
        @PathVariable productId: Long,
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): ResponseEntity<String> {
        return if (favoriteService.removeFavorite(userDetails.user.id!!, productId))
            ResponseEntity.ok("Removed from favorites.")
        else
            ResponseEntity.badRequest().body("User or product not found.")
    }

    @GetMapping
    fun listFavorites(
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): List<ProductEntity> {
        return favoriteService.getFavorites(userDetails.user.id!!)
    }
}
