package hr.eduwalk.routes

import hr.eduwalk.data.model.Question
import hr.eduwalk.domain.model.EmptyResponse
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

            val questionResponse = updateOrInsertQuestion(question = question)
            val httpStatusCode = if (questionResponse.error == null) {
                HttpStatusCode.Created
            } else {
                HttpStatusCode.BadRequest
            }

            call.respond(status = httpStatusCode, message = questionResponse)
        }
        route(path = "{questionId}") {
            post("update") {
                val question = call.receive<Question>()

                val questionResponse = updateOrInsertQuestion(question = question)
                val httpStatusCode = if (questionResponse.error == null) {
                    HttpStatusCode.OK
                } else {
                    HttpStatusCode.BadRequest
                }

                call.respond(
                    status = httpStatusCode,
                    message = EmptyResponse(data = questionResponse.question?.let { }, error = questionResponse.error),
                )
            }
            delete {
                val questionId = call.parameters.getOrFail<Long>("questionId")

                val emptyResponse = deleteQuestion(questionId = questionId)
                val httpStatusCode = if (emptyResponse.error == null) HttpStatusCode.OK else HttpStatusCode.BadRequest

                call.respond(status = httpStatusCode, message = emptyResponse)
            }
        }
        get(path = "{locationId}") {
            val locationId = call.parameters.getOrFail<Long>("locationId")

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
