package hr.eduwalk.data.dao

import hr.eduwalk.data.database.DatabaseFactory
import hr.eduwalk.data.database.table.QuestionTable
import hr.eduwalk.data.model.Question
import hr.eduwalk.domain.interfaces.IQuestionDao
import hr.eduwalk.domain.model.ErrorCode
import hr.eduwalk.domain.model.ResponseError
import hr.eduwalk.domain.model.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.sqlite.SQLiteErrorCode

class QuestionDaoImpl : IQuestionDao {

    override suspend fun insertQuestion(question: Question): ServiceResult<Unit> = try {
        DatabaseFactory.dbQuery {
            QuestionTable.insert {
                it[questionText] = question.questionText
                it[answer0] = question.answers[0]
                it[answer1] = question.answers[1]
                it[answer2] = question.answers.getOrNull(2)
                it[answer3] = question.answers.getOrNull(3)
                it[answer4] = question.answers.getOrNull(4)
                it[correctAnswer] = question.correctAnswer
                it[locationId] = question.locationId
            }.resultedValues?.singleOrNull()?.let {
                ServiceResult.Success(data = Unit)
            } ?: ServiceResult.Error(error = ResponseError(errorCode = ErrorCode.DATABASE_ERROR))
        }
    } catch (e: Exception) {
        val errorCode = when (e) {
            is ExposedSQLException -> {
                if (e.errorCode == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                    ErrorCode.QUESTION_EXISTS
                } else {
                    ErrorCode.DATABASE_ERROR
                }
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun updateQuestion(question: Question): ServiceResult<Boolean> = try {
        val dbUpdateResult = DatabaseFactory.dbQuery {
            QuestionTable.update(
                where = { QuestionTable.id eq question.id },
                body = {
                    it[questionText] = question.questionText
                    it[answer0] = question.answers[0]
                    it[answer1] = question.answers[1]
                    it[answer2] = question.answers.getOrNull(2)
                    it[answer3] = question.answers.getOrNull(3)
                    it[answer4] = question.answers.getOrNull(4)
                    it[correctAnswer] = question.correctAnswer
                }
            )
        }
        ServiceResult.Success(data = dbUpdateResult == 1)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is NoSuchElementException -> ErrorCode.UNKNOWN_LOCATION
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun deleteQuestion(questionId: Long): ServiceResult<Unit>  = try {
        val dbDeleteResult = DatabaseFactory.dbQuery {
            QuestionTable.deleteWhere { QuestionTable.id eq questionId }
        }
        if (dbDeleteResult == 0) throw RuntimeException()
        ServiceResult.Success(data = Unit)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is RuntimeException -> ErrorCode.UNKNOWN_QUESTION
            is ExposedSQLException -> {
                println("Exception from deleteQuestion(): ${e.errorCode}")
                ErrorCode.DATABASE_ERROR
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun getQuestionsForLocation(locationId: Long): ServiceResult<List<Question>> = try {
        val dbQuestions = DatabaseFactory.dbQuery {
            QuestionTable.select { QuestionTable.locationId eq locationId }.map(::resultRowToQuestion)
        }
        ServiceResult.Success(data = dbQuestions)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is ExposedSQLException -> {
                println("Exception from getQuestionsForLocation(): ${e.errorCode}")
                ErrorCode.DATABASE_ERROR
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    private fun resultRowToQuestion(row: ResultRow) = Question(
        id = row[QuestionTable.id],
        questionText = row[QuestionTable.questionText],
        answers = mutableListOf(row[QuestionTable.answer0], row[QuestionTable.answer1]).apply {
            row[QuestionTable.answer2]?.let { add(it) }
            row[QuestionTable.answer3]?.let { add(it) }
            row[QuestionTable.answer4]?.let { add(it) }
        },
        correctAnswer = row[QuestionTable.correctAnswer],
        locationId = row[QuestionTable.locationId],
    )
}
