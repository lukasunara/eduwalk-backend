package hr.eduwalk.data.model

data class Score(
    val userId: String,
    val questionId: Int,
    val isCorrect: Int,
)
