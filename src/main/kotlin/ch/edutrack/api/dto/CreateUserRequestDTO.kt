package ch.edutrack.api.dto

data class CreateUserRequestDTO(
    val username: String,
    val email: String,
    val password: String,
    val firstName: String?,
    val lastName: String?,
    val roleIds: List<Long>
)
