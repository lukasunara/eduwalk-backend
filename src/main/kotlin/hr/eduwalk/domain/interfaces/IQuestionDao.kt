package hr.eduwalk.domain.interfaces

import hr.eduwalk.data.model.Question
import hr.eduwalk.domain.model.ServiceResult

interface IQuestionDao {
    suspend fun insertQuestion(question: Question): ServiceResult<Unit>
    suspend fun updateQuestion(question: Question): ServiceResult<Boolean>
    suspend fun getQuestionsForLocation(locationId: Int): ServiceResult<List<Question>>
}
