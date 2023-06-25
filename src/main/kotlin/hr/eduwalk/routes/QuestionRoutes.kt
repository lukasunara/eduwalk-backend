package hr.eduwalk.routes

import hr.eduwalk.data.model.Question
import hr.eduwalk.domain.usecase.question.DeleteQuestion
import hr.eduwalk.domain.usecase.question.GetLocationQuestions
import hr.eduwalk.domain.usecase.question.UpdateOrInsertQuestion
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

fun Route.questionRoutes(
    deleteQuestion: DeleteQuestion,
    getLocationQuestions: GetLocationQuestions,
    updateOrInsertQuestion: UpdateOrInsertQuestion,
) {
    route(path = "question") {
        post(path = "create") {
            val question = call.receive<Question>()

            val emptyResponse = updateOrInsertQuestion(question = question)
            val httpStatusCode = if (emptyResponse.error == null) HttpStatusCode.Created else HttpStatusCode.BadRequest

            call.respond(status = httpStatusCode, message = emptyResponse)
        }
        route(path = "{questionId}") {
            post("update") {
                val question = call.receive<Question>()

                val emptyResponse = updateOrInsertQuestion(question = question)
                val httpStatusCode = if (emptyResponse.error == null) HttpStatusCode.OK else HttpStatusCode.BadRequest

                call.respond(status = httpStatusCode, message = emptyResponse)
            }
            delete {
                val questionId = call.parameters.getOrFail<Int>("questionId")

                val emptyResponse = deleteQuestion(questionId = questionId)
                val httpStatusCode = if (emptyResponse.error == null) HttpStatusCode.OK else HttpStatusCode.BadRequest

                call.respond(status = httpStatusCode, message = emptyResponse)
            }
        }
        get(path = "{locationId}") {
            val locationId = call.parameters.getOrFail<Int>("locationId")

            val locationQuestionsResponse = getLocationQuestions(locationId = locationId)
            val httpStatusCode = if (locationQuestionsResponse.error == null) {
                HttpStatusCode.OK
            } else {
                HttpStatusCode.BadRequest
            }

            call.respond(status = httpStatusCode, message = locationQuestionsResponse)
        }
    }
}
