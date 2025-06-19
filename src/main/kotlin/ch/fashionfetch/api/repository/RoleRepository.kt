package ch.fashionfetch.api.repository

import ch.fashionfetch.api.model.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<RoleEntity, Long> {
    fun findByName(name: String): RoleEntity?

}
