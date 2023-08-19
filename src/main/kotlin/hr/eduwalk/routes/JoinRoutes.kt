package hr.eduwalk.routes

import hr.eduwalk.domain.usecase.join.GetLocationsWithScores
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.util.getOrFail

fun Route.joinRoutes(
    getLocationsWithScores: GetLocationsWithScores,
) {
    route(path = "join") {
        get(path = "getLocationsWithScores") {
            val walkId = call.request.queryParameters.getOrFail("walkId")
            val username = call.request.queryParameters.getOrFail("username")

            val locationsWithScoresResponse = getLocationsWithScores(walkId = walkId, userId = username)
            val httpStatusCode = if (locationsWithScoresResponse.error == null) {
                HttpStatusCode.OK
            } else {
                HttpStatusCode.BadRequest
            }

            call.respond(status = httpStatusCode, message = locationsWithScoresResponse)
        }
    }
}
