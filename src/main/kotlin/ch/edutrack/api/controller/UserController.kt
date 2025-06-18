package ch.edutrack.api.controller

import ch.edutrack.api.dto.CreateUserRequestDTO
import ch.edutrack.api.dto.UserResponseDTO
import ch.edutrack.api.service.UserService
import jakarta.annotation.security.RolesAllowed
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    @RolesAllowed("ADMIN")
    fun getAll(): ResponseEntity<List<UserResponseDTO>> = ResponseEntity.ok(userService.getAll())

    @PostMapping
    @RolesAllowed("ADMIN")
    fun create(@RequestBody body: CreateUserRequestDTO): ResponseEntity<UserResponseDTO> =
        ResponseEntity.ok(userService.create(body))

    @DeleteMapping("/{id}")
    @RolesAllowed("ADMIN")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        userService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
