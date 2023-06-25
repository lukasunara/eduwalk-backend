package hr.eduwalk.domain.usecase

import hr.eduwalk.domain.interfaces.IQuestionDao
import hr.eduwalk.domain.model.EmptyResponse
import hr.eduwalk.domain.model.toEmptyResponse

class DeleteQuestion(
    private val questionDao: IQuestionDao,
) {
    suspend operator fun invoke(questionId: Int): EmptyResponse =
        questionDao.deleteQuestion(questionId = questionId).toEmptyResponse()
}
