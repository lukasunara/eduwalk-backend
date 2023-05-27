package hr.eduwalk.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseError(
    val code: ErrorCode,
    val message: String,
) {
    constructor(errorCode: ErrorCode) : this(code = errorCode, message = errorCode.message)
}

enum class ErrorCode(val message: String) {

    UNKNOWN_USER("This user doesn't exist."),

    USER_EXISTS("This user already exists."),
    QUESTION_EXISTS("This question already exists."),

    DATABASE_ERROR("Unknown database error. Try again, and check your parameters."),
    INVALID_JSON("Your JSON must match the format in this sample response."),
}
