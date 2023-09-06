package hr.eduwalk.domain.usecase.question

import hr.eduwalk.domain.interfaces.IQuestionDao
import hr.eduwalk.domain.model.EmptyResponse
import hr.eduwalk.domain.model.toEmptyResponse

class DeleteQuestion(
    private val questionDao: IQuestionDao,
) {
    suspend operator fun invoke(questionId: Long): EmptyResponse =
        questionDao.deleteQuestion(questionId = questionId).toEmptyResponse()
}
