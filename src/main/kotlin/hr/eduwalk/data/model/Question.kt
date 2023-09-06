package hr.eduwalk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Long,
    val questionText: String,
    val answers: List<String>,
    val correctAnswer: String,
    val locationId: Long,
)
