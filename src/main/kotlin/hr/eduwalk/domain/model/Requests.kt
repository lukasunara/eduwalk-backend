package hr.eduwalk.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateWalkRequestBody(
    val title: String,
    val description: String?,
)
