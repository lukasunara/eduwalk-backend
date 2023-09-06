package hr.eduwalk.routes

import hr.eduwalk.data.model.LocationScore
import hr.eduwalk.data.model.WalkScore
import hr.eduwalk.domain.model.EmptyResponse
import hr.eduwalk.domain.usecase.location.GetLocation
import hr.eduwalk.domain.usecase.locationscore.GetLocationScoreForUser
import hr.eduwalk.domain.usecase.locationscore.UpdateOrInsertLocationScore
import hr.eduwalk.domain.usecase.walkscore.GetWalkScoreForUser
import hr.eduwalk.domain.usecase.walkscore.UpdateOrInsertWalkScore
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
    updateOrInsertWalkScore: UpdateOrInsertWalkScore,
    getLocation: GetLocation,
    getWalkScoreForUser: GetWalkScoreForUser,
) {
    route(path = "locationScore") {
        post(path = "createOrUpdate") {
            val locationScore = call.receive<LocationScore>()

            val emptyResponse1 = updateOrInsertLocationScore(locationScore = locationScore)
            if (emptyResponse1.error != null) {
                call.respond(status = HttpStatusCode.BadRequest, message = emptyResponse1)
            }

            val locationResponse = getLocation(locationId = locationScore.locationId)
            if (locationResponse.error != null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = EmptyResponse(error = locationResponse.error),
                )
            }

            val walkScoreResponse = getWalkScoreForUser(
                walkId = locationResponse.location!!.walkId,
                userId = locationScore.userId,
            )
            if (walkScoreResponse.error != null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = EmptyResponse(error = walkScoreResponse.error),
                )
            }

            val locationScoreResponse = getLocationScoreForUser(
                locationId = locationScore.locationId,
                userId = locationScore.userId,
            )
            if (locationScoreResponse.error != null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = EmptyResponse(error = locationScoreResponse.error),
                )
            }

            val emptyResponse2 = updateOrInsertWalkScore(
                walkScore = WalkScore(
                    userId = locationScore.userId,
                    walkId = locationResponse.location.walkId,
                    score = (walkScoreResponse.walkScore!!.score ?: 0) -
                            (locationScoreResponse.locationScore!!.score ?: 0) +
                            (locationScore.score ?: 0),
                )
            )
            val httpStatusCode = if (emptyResponse2.error == null) HttpStatusCode.OK else HttpStatusCode.BadRequest

            call.respond(status = httpStatusCode, message = emptyResponse2)
        }
        get(path = "getLocationScoreForUser") {
            val locationId = call.request.queryParameters.getOrFail<Long>("locationId")
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
