package hr.eduwalk.data.model

data class Walk(
    val id: String,
    val title: String,
    val description: String? = null,
    val creatorId: String,
)
