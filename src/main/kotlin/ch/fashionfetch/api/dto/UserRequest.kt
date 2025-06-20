package ch.fashionfetch.api.dto

data class UserRequest(
    val username: String,
    val password: String,
    val email: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val isActive: Boolean = true,
    val roles: Set<String> = setOf("USER")
)
