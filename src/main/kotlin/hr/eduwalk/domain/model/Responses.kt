package hr.eduwalk.domain.model

import hr.eduwalk.data.model.Location
import hr.eduwalk.data.model.LocationScore
import hr.eduwalk.data.model.LocationWithScore
import hr.eduwalk.data.model.Question
import hr.eduwalk.data.model.User
import hr.eduwalk.data.model.Walk
import hr.eduwalk.data.model.WalkScore
import hr.eduwalk.data.model.WalkWithScore
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val user: User? = null,
    val error: ResponseError? = null,
)

@Serializable
data class LocationResponse(
    val location: Location? = null,
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
data class LocationsWithScoresResponse(
    val locationsWithScores: List<LocationWithScore>? = null,
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

@Serializable
data class WalkIdsResponse(
    val walkIds: List<String>? = null,
    val error: ResponseError? = null,
)

@Serializable
data class WalksWithScoresResponse(
    val walksWithScores: MutableList<WalkWithScore>? = null,
    val error: ResponseError? = null,
)

@Serializable
data class EmptyResponse(
    val data: Unit? = null,
    val error: ResponseError? = null,
)

@Serializable
data class QuestionResponse(
    val question: Question? = null,
    val error: ResponseError? = null,
)
