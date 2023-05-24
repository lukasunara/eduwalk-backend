package hr.eduwalk.routes

import hr.eduwalk.data.model.User
import hr.eduwalk.domain.usecase.GetOrInsertUser
import hr.eduwalk.domain.usecase.GetUser
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.util.getOrFail

fun Route.userRoutes(
    getUser: GetUser,
    getOrInsertUser: GetOrInsertUser,
) {
    route(path = "users") {
        post {
            val user = call.receive<User>()

            val userResponse = getOrInsertUser(user = user)
            val httpStatusCode =
                if (userResponse.errors.isEmpty()) HttpStatusCode.Created else HttpStatusCode.BadRequest

            call.respond(status = httpStatusCode, message = userResponse)
        }
        get(path = "{username}") {
            val username = call.parameters.getOrFail("username")

            val userResponse = getUser(username = username)
            val httpStatusCode = if (userResponse.errors.isEmpty()) HttpStatusCode.OK else HttpStatusCode.BadRequest

            call.respond(status = httpStatusCode, message = userResponse)
        }
    }
}
