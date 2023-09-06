package hr.eduwalk.domain.usecase.question

import hr.eduwalk.domain.interfaces.IQuestionDao
import hr.eduwalk.domain.model.LocationQuestionsResponse
import hr.eduwalk.domain.model.toLocationQuestionsResponse

class GetLocationQuestions(
    private val questionDao: IQuestionDao,
) {
    suspend operator fun invoke(locationId: Long): LocationQuestionsResponse =
        questionDao.getQuestionsForLocation(locationId = locationId).toLocationQuestionsResponse()
}
