package hr.eduwalk.domain.usecase

import hr.eduwalk.data.model.Question
import hr.eduwalk.domain.interfaces.IQuestionDao
import hr.eduwalk.domain.model.ServiceResult

class InsertQuestion(
    private val questionDao: IQuestionDao,
) {
    suspend operator fun invoke(question: Question): ServiceResult<Unit> =
        questionDao.insertQuestion(question = question)
}
