package hr.eduwalk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationScore(
    val userId: String,
    val locationId: Int,
    val score: Float? = null,
)
