package hr.eduwalk.routes

import hr.eduwalk.data.model.Location
import hr.eduwalk.domain.usecase.location.DeleteLocation
import hr.eduwalk.domain.usecase.location.GetWalkLocations
import hr.eduwalk.domain.usecase.location.UpdateOrInsertLocation
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

fun Route.locationRoutes(
    deleteLocation: DeleteLocation,
    getWalkLocations: GetWalkLocations,
    updateOrInsertLocation: UpdateOrInsertLocation,
) {
    route(path = "location") {
        post(path = "create") {
            val location = call.receive<Location>()

            val emptyResponse = updateOrInsertLocation(location = location)
            val httpStatusCode = if (emptyResponse.error == null) HttpStatusCode.Created else HttpStatusCode.BadRequest

            call.respond(status = httpStatusCode, message = emptyResponse)
        }
        route(path = "{locationId}") {
            post("update") {
                val location = call.receive<Location>()

                val emptyResponse = updateOrInsertLocation(location = location)
                val httpStatusCode = if (emptyResponse.error == null) HttpStatusCode.OK else HttpStatusCode.BadRequest

                call.respond(status = httpStatusCode, message = emptyResponse)
            }
            delete {
                val locationId = call.parameters.getOrFail<Int>("locationId")

                val emptyResponse = deleteLocation(locationId = locationId)
                val httpStatusCode = if (emptyResponse.error == null) HttpStatusCode.OK else HttpStatusCode.BadRequest

                call.respond(status = httpStatusCode, message = emptyResponse)
            }
        }
        get(path = "{walkId}") {
            val walkId = call.parameters.getOrFail("walkId")

            val walkLocationsResponse = getWalkLocations(walkId = walkId)
            val httpStatusCode = if (walkLocationsResponse.error == null) {
                HttpStatusCode.OK
            } else {
                HttpStatusCode.BadRequest
            }

            call.respond(status = httpStatusCode, message = walkLocationsResponse)
        }
    }
}
