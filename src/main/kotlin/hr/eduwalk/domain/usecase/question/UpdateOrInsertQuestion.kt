package hr.eduwalk.domain.usecase.question

import hr.eduwalk.data.model.Question
import hr.eduwalk.domain.interfaces.IQuestionDao
import hr.eduwalk.domain.model.QuestionResponse
import hr.eduwalk.domain.model.ServiceResult
import hr.eduwalk.domain.model.toQuestionResponse

class UpdateOrInsertQuestion(
    private val questionDao: IQuestionDao,
) {
    suspend operator fun invoke(question: Question): QuestionResponse {
        questionDao.updateQuestion(question = question).also { result ->
            if (result is ServiceResult.Success && result.data) {
                return ServiceResult.Success(data = question).toQuestionResponse()
            }
        }
        return questionDao.insertQuestion(question = question).toQuestionResponse()
    }
}
