package hr.eduwalk.domain.model

import hr.eduwalk.data.model.Location
import hr.eduwalk.data.model.LocationScore
import hr.eduwalk.data.model.LocationWithScore
import hr.eduwalk.data.model.Question
import hr.eduwalk.data.model.User
import hr.eduwalk.data.model.Walk
import hr.eduwalk.data.model.WalkScore

sealed class ServiceResult<out T> {
    data class Success<out T>(val data: T) : ServiceResult<T>()
    data class Error(val error: ResponseError) : ServiceResult<Nothing>()
}

fun ServiceResult<User>.toUserResponse() = when (this) {
    is ServiceResult.Success -> UserResponse(user = data)
    is ServiceResult.Error -> UserResponse(error = error)
}

fun ServiceResult<List<Question>>.toLocationQuestionsResponse() = when (this) {
    is ServiceResult.Success -> LocationQuestionsResponse(questions = data)
    is ServiceResult.Error -> LocationQuestionsResponse(error = error)
}

fun ServiceResult<LocationScore>.toLocationScoreResponse() = when (this) {
    is ServiceResult.Success -> LocationScoreResponse(locationScore = data)
    is ServiceResult.Error -> LocationScoreResponse(error = error)
}

fun ServiceResult<List<LocationWithScore>>.toLocationsWithScoresResponse() = when (this) {
    is ServiceResult.Success -> LocationsWithScoresResponse(locationsWithScores = data)
    is ServiceResult.Error -> LocationsWithScoresResponse(error = error)
}

fun ServiceResult<WalkScore>.toWalkScoreResponse() = when (this) {
    is ServiceResult.Success -> WalkScoreResponse(walkScore = data)
    is ServiceResult.Error -> WalkScoreResponse(error = error)
}

fun ServiceResult<List<WalkScore>>.toWalkScoreTop5Response() = when (this) {
    is ServiceResult.Success -> WalkScoreTop5Response(walkScores = data)
    is ServiceResult.Error -> WalkScoreTop5Response(error = error)
}

fun ServiceResult<List<Location>>.toWalkLocationsResponse() = when (this) {
    is ServiceResult.Success -> WalkLocationsResponse(locations = data)
    is ServiceResult.Error -> WalkLocationsResponse(error = error)
}

fun ServiceResult<Walk>.toWalkResponse() = when (this) {
    is ServiceResult.Success -> WalkResponse(walk = data)
    is ServiceResult.Error -> WalkResponse(error = error)
}

fun ServiceResult<List<Walk>>.toWalksResponse() = when (this) {
    is ServiceResult.Success -> WalksResponse(walks = data)
    is ServiceResult.Error -> WalksResponse(error = error)
}

fun ServiceResult<List<String>>.toWalkIdsResponse() = when (this) {
    is ServiceResult.Success -> WalkIdsResponse(walkIds = data)
    is ServiceResult.Error -> WalkIdsResponse(error = error)
}

fun ServiceResult<Unit>.toEmptyResponse() = when (this) {
    is ServiceResult.Success -> EmptyResponse(data = data)
    is ServiceResult.Error -> EmptyResponse(error = error)
}
