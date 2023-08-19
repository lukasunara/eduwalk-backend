package hr.eduwalk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationWithScore(
    val location: Location,
    val score: Int? = null,
)
