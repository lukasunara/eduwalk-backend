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
    UNKNOWN_WALK_SCORE("This walk has no score for given user."),
    UNKNOWN_LOCATION_SCORE("This location has no score for given user."),
    UNKNOWN_LOCATION("This location doesn't exist."),

    USER_EXISTS("This user already exists."),
    QUESTION_EXISTS("This question already exists."),
    WALK_SCORE_EXISTS("This walk score already exists."),
    LOCATION_SCORE_EXISTS("This location score already exists."),
    LOCATION_EXISTS("This location already exists."),

    DATABASE_ERROR("Unknown database error. Try again, and check your parameters."),
    INVALID_JSON("Your JSON must match the format in this sample response."),
}
