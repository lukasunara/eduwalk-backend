package hr.eduwalk.plugins

import hr.eduwalk.data.model.User
import hr.eduwalk.domain.model.ErrorCode
import hr.eduwalk.domain.model.ResponseError
import hr.eduwalk.domain.model.UserResponse
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
//                            startsWith(AppRoutes.APPS_ROUTE) -> sendBadApp(call)
//                            startsWith(AppRoutes.APP_ROUTE) -> sendBadApp(call)
                        }
                    }
                else -> call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}

//suspend fun sendBadApp(call: ApplicationCall) {
//    val sampleApp = UserAppResponse(
//        listOf(
//            UserWithApp(
//                email = "sample@email.com",
//                appName = "Your App's Name", appType = AppType.DEVELOPMENT
//            )
//        ),
//        listOf(ResponseErrors(ErrorCode.INVALID_JSON, ErrorCode.INVALID_JSON.message))
//    )
//
//    call.respond(
//        status = HttpStatusCode.BadRequest,
//        message = sampleApp
//    )
//}

suspend fun sendBadUser(call: ApplicationCall) {
    val sampleUser = UserResponse(
        user = User(username = "username"),
        errors = listOf(ResponseError(ErrorCode.INVALID_JSON, ErrorCode.INVALID_JSON.message)),
    )
    call.respond(status = HttpStatusCode.BadRequest, message = sampleUser)
}
