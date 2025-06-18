package ch.edutrack.api.repository

import ch.edutrack.api.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
    fun findAllByIsActiveIsTrue(): List<UserEntity>
}