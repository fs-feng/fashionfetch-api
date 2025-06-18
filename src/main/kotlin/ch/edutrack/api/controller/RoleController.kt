package ch.edutrack.api.controller

import ch.edutrack.api.model.RoleEntity
import ch.edutrack.api.repository.RoleRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/roles")
class RoleController(private val roleRepository: RoleRepository) {

    @GetMapping
    fun getAllRoles(): List<RoleEntity> = roleRepository.findAll()
}
