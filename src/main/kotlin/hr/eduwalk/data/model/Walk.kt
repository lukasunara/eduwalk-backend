package hr.eduwalk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Walk(
    val id: String,
    val title: String,
    val description: String? = null,
    val creatorId: String? = null,
)
