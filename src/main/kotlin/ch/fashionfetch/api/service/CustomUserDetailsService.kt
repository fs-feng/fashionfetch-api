package ch.fashionfetch.api.service

import ch.fashionfetch.api.config.CustomUserDetails
import ch.fashionfetch.api.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User not found")

        val authorities = user.roles.map { role -> SimpleGrantedAuthority(role.name) }.toSet()

        return CustomUserDetails(user)
    }
}