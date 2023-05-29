package hr.eduwalk.data.dao

import hr.eduwalk.data.database.DatabaseFactory
import hr.eduwalk.data.database.table.WalkScoreTable
import hr.eduwalk.data.model.WalkScore
import hr.eduwalk.domain.interfaces.IWalkScoreDao
import hr.eduwalk.domain.model.ErrorCode
import hr.eduwalk.domain.model.ResponseError
import hr.eduwalk.domain.model.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.sqlite.SQLiteErrorCode

class WalkScoreDaoImpl : IWalkScoreDao {

    override suspend fun insertWalkScore(walkScore: WalkScore): ServiceResult<Unit> = try {
        DatabaseFactory.dbQuery {
            WalkScoreTable.insert {
                it[walkId] = walkScore.walkId
                it[userId] = walkScore.userId
                it[score] = walkScore.score
            }.resultedValues?.singleOrNull()?.let {
                ServiceResult.Success(data = Unit)
            } ?: ServiceResult.Error(error = ResponseError(errorCode = ErrorCode.DATABASE_ERROR))
        }
    } catch (e: Exception) {
        val errorCode = when (e) {
            is ExposedSQLException -> {
                if (e.errorCode == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                    ErrorCode.WALK_SCORE_EXISTS
                } else {
                    ErrorCode.DATABASE_ERROR
                }
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun updateWalkScore(walkScore: WalkScore): ServiceResult<Boolean> = try {
        val dbUpdateResult = DatabaseFactory.dbQuery {
            WalkScoreTable.update(
                where = {
                    WalkScoreTable.userId eq walkScore.userId
                    WalkScoreTable.walkId eq walkScore.walkId
                },
                body = { it[score] = walkScore.score }
            )
        }
        ServiceResult.Success(data = dbUpdateResult == 1)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is NoSuchElementException -> ErrorCode.UNKNOWN_WALK_SCORE
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun getWalkScoreForUser(walkId: String, userId: String): ServiceResult<WalkScore> = try {
        val dbWalkScore = DatabaseFactory.dbQuery {
            WalkScoreTable.select {
                WalkScoreTable.userId eq userId
                WalkScoreTable.walkId eq walkId
            }.map(::resultRowToWalkScore).single()
        }
        ServiceResult.Success(data = dbWalkScore)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is NoSuchElementException -> ErrorCode.UNKNOWN_WALK_SCORE
            is ExposedSQLException -> {
                println("Exception from getWalkScoreForUser(): ${e.errorCode}")
                ErrorCode.DATABASE_ERROR
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun getWalkScoreTop5(walkId: String): ServiceResult<List<WalkScore>> = try {
        val dbWalkScores = DatabaseFactory.dbQuery {
            WalkScoreTable
                .select {
                    WalkScoreTable.walkId eq walkId
                    WalkScoreTable.score.isNotNull()
                }
                .orderBy(column = WalkScoreTable.score, order = SortOrder.DESC)
                .limit(n = 5)
                .map(::resultRowToWalkScore)
        }
        ServiceResult.Success(data = dbWalkScores)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is ExposedSQLException -> {
                println("Exception from getWalkScoreTop5(): ${e.errorCode}")
                ErrorCode.DATABASE_ERROR
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun getUserParticipatedWalkIds(userId: String): ServiceResult<List<String>> = try {
        val dbWalkIds = DatabaseFactory.dbQuery {
            WalkScoreTable
                .select {
                    WalkScoreTable.userId eq userId
                    WalkScoreTable.score.isNotNull()
                }
                .orderBy(column = WalkScoreTable.score, order = SortOrder.DESC)
                .map { it[WalkScoreTable.walkId] }
        }
        ServiceResult.Success(data = dbWalkIds)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is ExposedSQLException -> {
                println("Exception from getUserParticipatedWalkIds(): ${e.errorCode}")
                ErrorCode.DATABASE_ERROR
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    private fun resultRowToWalkScore(row: ResultRow) = WalkScore(
        userId = row[WalkScoreTable.userId],
        walkId = row[WalkScoreTable.walkId],
        score = row[WalkScoreTable.score],
    )
}
