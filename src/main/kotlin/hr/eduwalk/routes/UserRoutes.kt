package hr.eduwalk.routes

import hr.eduwalk.data.model.User
import hr.eduwalk.domain.usecase.user.DeleteUser
import hr.eduwalk.domain.usecase.user.GetOrInsertUser
import hr.eduwalk.domain.usecase.user.GetUser
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.util.getOrFail

fun Route.userRoutes(
    getUser: GetUser,
    deleteUser: DeleteUser,
    getOrInsertUser: GetOrInsertUser,
) {
    route(path = "users") {
        post(path = "create") {
            val user = call.receive<User>()

            val userResponse = getOrInsertUser(user = user)
            val httpStatusCode = if (userResponse.error == null) HttpStatusCode.Created else HttpStatusCode.BadRequest

            call.respond(status = httpStatusCode, message = userResponse)
        }
        route(path = "{username}") {
            get {
                val username = call.parameters.getOrFail("username")

                val userResponse = getUser(username = username)
                val httpStatusCode = if (userResponse.error == null) HttpStatusCode.OK else HttpStatusCode.BadRequest

                call.respond(status = httpStatusCode, message = userResponse)
            }
            delete {
                val username = call.parameters.getOrFail("username")

                val emptyResponse = deleteUser(username = username)
                val httpStatusCode = if (emptyResponse.error == null) HttpStatusCode.OK else HttpStatusCode.BadRequest

                call.respond(status = httpStatusCode, message = emptyResponse)
            }
        }
    }
}
