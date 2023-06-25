package hr.eduwalk.routes

import hr.eduwalk.data.model.LocationScore
import hr.eduwalk.domain.usecase.locationscore.GetLocationScoreForUser
import hr.eduwalk.domain.usecase.locationscore.UpdateOrInsertLocationScore
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.util.getOrFail

fun Route.locationScoreRoutes(
    getLocationScoreForUser: GetLocationScoreForUser,
    updateOrInsertLocationScore: UpdateOrInsertLocationScore,
) {
    route(path = "locationScore") {
        post(path = "createOrUpdate") {
            val locationScore = call.receive<LocationScore>()

            val emptyResponse = updateOrInsertLocationScore(locationScore = locationScore)
            val httpStatusCode = if (emptyResponse.error == null) HttpStatusCode.OK else HttpStatusCode.BadRequest

            call.respond(status = httpStatusCode, message = emptyResponse)
        }
        get(path = "getLocationScoreForUser") {
            val locationId = call.request.queryParameters.getOrFail<Int>("locationId")
            val username = call.request.queryParameters.getOrFail("username")

            val locationScoreResponse = getLocationScoreForUser(locationId = locationId, userId = username)
            val httpStatusCode = if (locationScoreResponse.error == null) {
                HttpStatusCode.OK
            } else {
                HttpStatusCode.BadRequest
            }

            call.respond(status = httpStatusCode, message = locationScoreResponse)
        }
    }
}
