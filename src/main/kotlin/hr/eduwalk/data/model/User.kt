package hr.eduwalk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val role: UserRole = UserRole.STUDENT,
)

enum class UserRole {
    STUDENT, TEACHER
}
