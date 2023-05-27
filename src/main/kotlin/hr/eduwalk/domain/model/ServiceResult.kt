package hr.eduwalk.domain.model

import hr.eduwalk.data.model.Question
import hr.eduwalk.data.model.User

sealed class ServiceResult<out T> {
    data class Success<out T>(val data: T) : ServiceResult<T>()
    data class Error(val error: ResponseError) : ServiceResult<Nothing>()
}

fun ServiceResult<User>.toUserResponse() = when (this) {
    is ServiceResult.Success -> UserResponse(user = data)
    is ServiceResult.Error -> UserResponse(error = error)
}
