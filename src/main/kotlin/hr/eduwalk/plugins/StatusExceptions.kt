package hr.eduwalk.plugins

import hr.eduwalk.data.model.User
import hr.eduwalk.data.model.Walk
import hr.eduwalk.domain.model.EmptyResponse
import hr.eduwalk.domain.model.ErrorCode
import hr.eduwalk.domain.model.ResponseError
import hr.eduwalk.domain.model.UserResponse
import hr.eduwalk.domain.model.WalkResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.ContentTransformationException
import io.ktor.server.request.path
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import kotlinx.serialization.SerializationException

fun Application.configureStatusExceptions() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            println("path: ${call.request.path()}")
            println(cause)

            when (cause) {
                is SerializationException,
                is ContentTransformationException,
                is BadRequestException,
                ->
                    with(call.request.path()) {
                        when {
                            startsWith("/users") -> sendBadUser(call)
                            startsWith("/walk/create") -> sendBadWalk(call)
                            startsWith("/walk") -> sendEmptyResponse(call)
                        }
                    }
                else -> call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}

suspend fun sendEmptyResponse(call: ApplicationCall) {
    val sampleUser = EmptyResponse(
        data = Unit,
        error = ResponseError(errorCode = ErrorCode.INVALID_JSON),
    )
    call.respond(status = HttpStatusCode.BadRequest, message = sampleUser)
}

suspend fun sendBadUser(call: ApplicationCall) {
    val sampleUser = UserResponse(
        user = User(username = "username"),
        error = ResponseError(errorCode = ErrorCode.INVALID_JSON),
    )
    call.respond(status = HttpStatusCode.BadRequest, message = sampleUser)
}

suspend fun sendBadWalk(call: ApplicationCall) {
    val sampleWalk = WalkResponse(
        walk = Walk(id = "code1234", title = "Title", description = "This is a description.", creatorId = "username"),
        error = ResponseError(errorCode = ErrorCode.INVALID_JSON),
    )
    call.respond(status = HttpStatusCode.BadRequest, message = sampleWalk)
}
