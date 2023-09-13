package hr.eduwalk.routes

import hr.eduwalk.domain.model.WalksWithScoresResponse
import hr.eduwalk.domain.usecase.join.GetLocationsWithScores
import hr.eduwalk.domain.usecase.join.GetWalksWithScores
import hr.eduwalk.domain.usecase.location.GetWalkLocations
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.util.getOrFail

fun Route.joinRoutes(
    getLocationsWithScores: GetLocationsWithScores,
    getWalksWithScores: GetWalksWithScores,
    getWalkLocations: GetWalkLocations,
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
        get(path = "getWalksWithScores") {
            val username = call.request.queryParameters.getOrFail("username")

            val walksWithScoresResponse = getWalksWithScores(userId = username)
            if (walksWithScoresResponse.error != null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = walksWithScoresResponse,
                )
            }

            walksWithScoresResponse.walksWithScores!!.forEach { walkWithScore ->
                val walkLocationsResponse = getWalkLocations(walkId = walkWithScore.walk.id)
                if (walkLocationsResponse.error != null) {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = WalksWithScoresResponse(error = walkLocationsResponse.error),
                    )
                    return@forEach
                }
                walkWithScore.maxScore = (walkLocationsResponse.locations?.size ?: 0) * 3
            }
            walksWithScoresResponse.walksWithScores.sortByDescending {
                if (it.maxScore == 0) 0 else it.score.toLong() / it.maxScore
            }

            call.respond(status = HttpStatusCode.OK, message = walksWithScoresResponse)
        }
    }
}
