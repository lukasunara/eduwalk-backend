package hr.eduwalk.domain.model

import hr.eduwalk.data.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val user: User? = null,
    val error: ResponseError? = null,
)
