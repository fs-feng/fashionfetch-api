package ch.fashionfetch.api.service

import ch.fashionfetch.api.dto.UserRequest
import ch.fashionfetch.api.model.UserEntity
import ch.fashionfetch.api.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun createUser(request: UserRequest): UserEntity {
        val user = UserEntity(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            email = request.email,
            firstName = request.firstName,
            lastName = request.lastName,
            isActive = request.isActive
        )
        return userRepository.save(user)
    }
}