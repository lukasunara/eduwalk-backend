package hr.eduwalk.domain.model

import hr.eduwalk.data.model.Location
import hr.eduwalk.data.model.LocationScore
import hr.eduwalk.data.model.Question
import hr.eduwalk.data.model.User
import hr.eduwalk.data.model.Walk
import hr.eduwalk.data.model.WalkScore
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val user: User? = null,
    val error: ResponseError? = null,
)

@Serializable
data class LocationQuestionsResponse(
    val questions: List<Question>? = null,
    val error: ResponseError? = null,
)

@Serializable
data class LocationScoreResponse(
    val locationScore: LocationScore? = null,
    val error: ResponseError? = null,
)

@Serializable
data class WalkScoreResponse(
    val walkScore: WalkScore? = null,
    val error: ResponseError? = null,
)

@Serializable
data class WalkScoreTop5Response(
    val walkScores: List<WalkScore>? = null,
    val error: ResponseError? = null,
)

@Serializable
data class WalkLocationsResponse(
    val locations: List<Location>? = null,
    val error: ResponseError? = null,
)

@Serializable
data class WalkResponse(
    val walk: Walk? = null,
    val error: ResponseError? = null,
)

@Serializable
data class WalksResponse(
    val walks: List<Walk>? = null,
    val error: ResponseError? = null,
)
