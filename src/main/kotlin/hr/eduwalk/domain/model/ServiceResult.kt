package hr.eduwalk.domain.model

import hr.eduwalk.data.model.Question
import hr.eduwalk.data.model.User
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

fun ServiceResult<WalkScore>.toWalkScoreResponse() = when (this) {
    is ServiceResult.Success -> WalkScoreResponse(walkScore = data)
    is ServiceResult.Error -> WalkScoreResponse(error = error)
}

fun ServiceResult<List<WalkScore>>.toWalkScoreTop5Response() = when (this) {
    is ServiceResult.Success -> WalkScoreTop5Response(walkScores = data)
    is ServiceResult.Error -> WalkScoreTop5Response(error = error)
}
