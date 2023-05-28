package hr.eduwalk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WalkScore(
    val userId: String,
    val walkId: String,
    val score: Float? = null,
)
