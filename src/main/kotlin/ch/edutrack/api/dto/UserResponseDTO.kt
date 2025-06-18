package ch.edutrack.api.dto

data class UserResponseDTO(
    val id: Long?,
    val username: String,
    val email: String,
    val firstName: String?,
    val lastName: String?,
    val isActive: Boolean,
    val roles: List<String>
)
