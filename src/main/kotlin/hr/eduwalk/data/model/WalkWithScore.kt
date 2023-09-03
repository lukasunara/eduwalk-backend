package hr.eduwalk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WalkWithScore(
    val walk: Walk,
    val score: Int,
    var maxScore: Int = 0,
)
