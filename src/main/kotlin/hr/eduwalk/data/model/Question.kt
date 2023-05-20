package hr.eduwalk.data.model

data class Question(
    val id: Int,
    val questionText: String,
    val answers: List<String>,
    val correctAnswer: String,
    val locationId: Int,
)
