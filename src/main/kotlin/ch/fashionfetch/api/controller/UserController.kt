package ch.fashionfetch.api.controller

import ch.fashionfetch.api.dto.UserRequest
import ch.fashionfetch.api.model.UserEntity
import ch.fashionfetch.api.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/create")
    fun createUser(@RequestBody userRequest: UserRequest): ResponseEntity<UserEntity> {
        val createdUser = userService.createUser(userRequest)
        return ResponseEntity.ok(createdUser)
    }
}