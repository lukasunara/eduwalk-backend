package hr.eduwalk.domain.usecase

import hr.eduwalk.data.model.Question
import hr.eduwalk.domain.interfaces.IQuestionDao
import hr.eduwalk.domain.model.ServiceResult

class UpdateOrInsertQuestion(
    private val questionDao: IQuestionDao,
) {
    suspend operator fun invoke(question: Question): ServiceResult<Unit> {
        questionDao.updateQuestion(question = question).also { result ->
            if (result is ServiceResult.Success && result.data) {
                return ServiceResult.Success(data = Unit)
            }
        }
        return questionDao.insertQuestion(question = question)
    }
}
