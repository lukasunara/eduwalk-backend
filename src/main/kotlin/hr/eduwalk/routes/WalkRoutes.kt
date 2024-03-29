package hr.eduwalk.routes

import hr.eduwalk.data.model.Walk
import hr.eduwalk.domain.model.UpdateWalkRequestBody
import hr.eduwalk.domain.usecase.walk.DeleteWalk
import hr.eduwalk.domain.usecase.walk.GetDefaultWalks
import hr.eduwalk.domain.usecase.walk.GetUserCreatedWalks
import hr.eduwalk.domain.usecase.walk.GetWalkById
import hr.eduwalk.domain.usecase.walk.InsertWalk
import hr.eduwalk.domain.usecase.walk.UpdateWalk
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

fun Route.walkRoutes(
    insertWalk: InsertWalk,
    updateWalk: UpdateWalk,
    deleteWalk: DeleteWalk,
    getWalkById: GetWalkById,
    getDefaultWalks: GetDefaultWalks,
    getUserCreatedWalks: GetUserCreatedWalks,
) {
    route(path = "walk") {
        post(path = "create") {
            val walk = call.receive<Walk>()

            val emptyResponse = insertWalk(walk = walk)
            val httpStatusCode = if (emptyResponse.error == null) HttpStatusCode.Created else HttpStatusCode.BadRequest

            call.respond(status = httpStatusCode, message = emptyResponse)
        }
        get(path = "getDefaultWalks") {
            val walksResponse = getDefaultWalks()
            val httpStatusCode = if (walksResponse.error == null) HttpStatusCode.OK else HttpStatusCode.BadRequest

            call.respond(status = httpStatusCode, message = walksResponse)
        }
        get(path = "getUserCreatedWalks") {
            val username = call.request.queryParameters.getOrFail("username")

            val walksResponse = getUserCreatedWalks(userId = username)
            val httpStatusCode = if (walksResponse.error == null) HttpStatusCode.OK else HttpStatusCode.BadRequest

            call.respond(status = httpStatusCode, message = walksResponse)
        }
        route(path = "{walkId}") {
            get {
                val walkId = call.parameters.getOrFail("walkId")

                val walkResponse = getWalkById(walkId = walkId)
                val httpStatusCode = if (walkResponse.error == null) HttpStatusCode.OK else HttpStatusCode.BadRequest

                call.respond(status = httpStatusCode, message = walkResponse)
            }
            post(path = "update") {
                val walkId = call.parameters.getOrFail("walkId")
                val updateWalkRequestBody = call.receive<UpdateWalkRequestBody>()

                val emptyResponse = updateWalk(walkId = walkId, updateWalkRequestBody = updateWalkRequestBody)
                val httpStatusCode = if (emptyResponse.error == null) HttpStatusCode.OK else HttpStatusCode.BadRequest

                call.respond(status = httpStatusCode, message = emptyResponse)
            }
            delete {
                val walkId = call.parameters.getOrFail("walkId")

                val emptyResponse = deleteWalk(walkId = walkId)
                val httpStatusCode = if (emptyResponse.error == null) HttpStatusCode.OK else HttpStatusCode.BadRequest

                call.respond(status = httpStatusCode, message = emptyResponse)
            }
        }
    }
}
