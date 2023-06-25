package hr.eduwalk.routes

import hr.eduwalk.data.model.WalkScore
import hr.eduwalk.domain.usecase.walkscore.GetUserParticipatedWalkIds
import hr.eduwalk.domain.usecase.walkscore.GetWalkScoreForUser
import hr.eduwalk.domain.usecase.walkscore.GetWalkScoreTop5
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

fun Route.walkScoreRoutes(
    getWalkScoreTop5: GetWalkScoreTop5,
    getWalkScoreForUser: GetWalkScoreForUser,
    updateOrInsertWalkScore: UpdateOrInsertWalkScore,
    getUserParticipatedWalkIds: GetUserParticipatedWalkIds,
) {
    route(path = "walkScore") {
        post(path = "createOrUpdate") {
            val walkScore = call.receive<WalkScore>()

            val emptyResponse = updateOrInsertWalkScore(walkScore = walkScore)
            val httpStatusCode = if (emptyResponse.error == null) HttpStatusCode.OK else HttpStatusCode.BadRequest

            call.respond(status = httpStatusCode, message = emptyResponse)
        }
        get(path = "getTop5") {
            val walkId = call.request.queryParameters.getOrFail("walkId")

            val walkScoreTop5Response = getWalkScoreTop5(walkId = walkId)
            val httpStatusCode = if (walkScoreTop5Response.error == null) {
                HttpStatusCode.OK
            } else {
                HttpStatusCode.BadRequest
            }

            call.respond(status = httpStatusCode, message = walkScoreTop5Response)
        }
        get(path = "getWalkScoreForUser") {
            val walkId = call.request.queryParameters.getOrFail("walkId")
            val username = call.request.queryParameters.getOrFail("username")

            val walkScoreResponse = getWalkScoreForUser(walkId = walkId, userId = username)
            val httpStatusCode = if (walkScoreResponse.error == null) {
                HttpStatusCode.OK
            } else {
                HttpStatusCode.BadRequest
            }

            call.respond(status = httpStatusCode, message = walkScoreResponse)
        }
        get(path = "getUserParticipatedWalkIds") {
            val username = call.request.queryParameters.getOrFail("username")

            val walkIdsResponse = getUserParticipatedWalkIds(userId = username)
            val httpStatusCode = if (walkIdsResponse.error == null) {
                HttpStatusCode.OK
            } else {
                HttpStatusCode.BadRequest
            }

            call.respond(status = httpStatusCode, message = walkIdsResponse)
        }
    }
}
