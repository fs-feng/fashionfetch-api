package ch.fashionfetch.api

import ch.fashionfetch.api.config.RsaKeyProperties
import ch.fashionfetch.api.model.RoleEntity
import ch.fashionfetch.api.model.UserEntity
import ch.fashionfetch.api.repository.RoleRepository
import ch.fashionfetch.api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableConfigurationProperties(RsaKeyProperties::class)
@SpringBootApplication
class FashionFetchApiApplication {

    @Bean
    fun createAdminUser(userRepository: UserRepository, roleRepository: RoleRepository, passwordEncoder: PasswordEncoder): ApplicationRunner {
        return ApplicationRunner {
            val hashedPassword = passwordEncoder.encode("password")

            val adminRole = roleRepository.findByName("ADMIN") ?: roleRepository.save(RoleEntity(name = "ADMIN"))

            // Prevent duplicate admin users
            if (userRepository.findByUsername("admin") == null) {
                val user = UserEntity(
                    username = "admin",
                    password = hashedPassword,
                    email = "admin@example.com",
                    firstName = "Admin",
                    lastName = "Admin",
                    isActive = true,
                    roles = mutableSetOf(adminRole) // Properly assigns role
                )
                userRepository.save(user)
                println("Admin user created successfully!")
            } else {
                println("Admin user already exists!")
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<FashionFetchApiApplication>(*args)
}
