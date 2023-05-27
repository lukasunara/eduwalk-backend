package hr.eduwalk.domain.usecase

import hr.eduwalk.domain.interfaces.IQuestionDao
import hr.eduwalk.domain.model.LocationQuestionsResponse
import hr.eduwalk.domain.model.toLocationQuestionsResponse

class GetLocationQuestions(
    private val questionDao: IQuestionDao,
) {
    suspend operator fun invoke(locationId: Int): LocationQuestionsResponse =
        questionDao.getQuestionsForLocation(locationId = locationId).toLocationQuestionsResponse()
}
