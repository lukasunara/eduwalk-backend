package hr.eduwalk.data.dao

import hr.eduwalk.data.database.DatabaseFactory
import hr.eduwalk.data.database.table.LocationScoreTable
import hr.eduwalk.data.model.LocationScore
import hr.eduwalk.domain.interfaces.ILocationScoreDao
import hr.eduwalk.domain.model.ErrorCode
import hr.eduwalk.domain.model.ResponseError
import hr.eduwalk.domain.model.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.sqlite.SQLiteErrorCode

class LocationScoreDaoImpl : ILocationScoreDao {

    override suspend fun insertLocationScore(locationScore: LocationScore): ServiceResult<Unit> = try {
        DatabaseFactory.dbQuery {
            LocationScoreTable.insert {
                it[userId] = locationScore.userId
                it[locationId] = locationScore.locationId
                it[score] = locationScore.score
            }.resultedValues?.singleOrNull()?.let {
                ServiceResult.Success(data = Unit)
            } ?: ServiceResult.Error(error = ResponseError(errorCode = ErrorCode.DATABASE_ERROR))
        }
    } catch (e: Exception) {
        val errorCode = when (e) {
            is ExposedSQLException -> {
                if (e.errorCode == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                    ErrorCode.LOCATION_SCORE_EXISTS
                } else {
                    ErrorCode.DATABASE_ERROR
                }
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun updateLocationScore(locationScore: LocationScore): ServiceResult<Boolean> = try {
        val dbUpdateResult = DatabaseFactory.dbQuery {
            LocationScoreTable.update(
                where = {
                    (LocationScoreTable.userId eq locationScore.userId) and
                            (LocationScoreTable.locationId eq locationScore.locationId)
                },
                body = { it[score] = locationScore.score }
            )
        }
        ServiceResult.Success(data = dbUpdateResult == 1)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is NoSuchElementException -> ErrorCode.UNKNOWN_LOCATION_SCORE
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun getLocationScoreForUser(locationId: Long, userId: String): ServiceResult<LocationScore> = try {
        val dbLocationScore = DatabaseFactory.dbQuery {
            LocationScoreTable.select {
                (LocationScoreTable.userId eq userId) and (LocationScoreTable.locationId eq locationId)
            }.map(::resultRowToLocationScore).single()
        }
        ServiceResult.Success(data = dbLocationScore)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is NoSuchElementException -> ErrorCode.UNKNOWN_LOCATION_SCORE
            is ExposedSQLException -> {
                println("Exception from getLocationScoreForUser(): ${e.errorCode}")
                ErrorCode.DATABASE_ERROR
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    private fun resultRowToLocationScore(row: ResultRow) = LocationScore(
        userId = row[LocationScoreTable.userId],
        locationId = row[LocationScoreTable.locationId],
        score = row[LocationScoreTable.score],
    )
}
