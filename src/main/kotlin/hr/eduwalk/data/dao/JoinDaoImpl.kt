package hr.eduwalk.data.dao

import hr.eduwalk.data.database.DatabaseFactory
import hr.eduwalk.data.database.table.LocationScoreTable
import hr.eduwalk.data.database.table.LocationTable
import hr.eduwalk.data.model.Location
import hr.eduwalk.data.model.LocationWithScore
import hr.eduwalk.domain.interfaces.IJoinDao
import hr.eduwalk.domain.model.ErrorCode
import hr.eduwalk.domain.model.ResponseError
import hr.eduwalk.domain.model.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select

class JoinDaoImpl : IJoinDao {

    override suspend fun getWalkLocationsWithScores(
        walkId: String,
        userId: String,
    ): ServiceResult<List<LocationWithScore>> = try {
        val dbLocationsWithScore = DatabaseFactory.dbQuery {
            (LocationTable leftJoin LocationScoreTable)
                .select {
                    LocationTable.walkId eq walkId and
                            (LocationScoreTable.userId eq userId or LocationScoreTable.userId.isNull())
                }
                .map(::resultRowToLocationWithScore)
        }
        ServiceResult.Success(data = dbLocationsWithScore)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is ExposedSQLException -> {
                println("Exception from getWalkLocationsWithScores(): ${e.errorCode}")
                ErrorCode.DATABASE_ERROR
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    private fun resultRowToLocationWithScore(row: ResultRow) = LocationWithScore(
        location = Location(
            id = row[LocationTable.id],
            latitude = row[LocationTable.latitude],
            longitude = row[LocationTable.longitude],
            title = row[LocationTable.title],
            description = row[LocationTable.description],
            imageBase64 = row[LocationTable.imageBase64],
            thresholdDistance = row[LocationTable.thresholdDistance],
            walkId = row[LocationTable.walkId],
        ),
        score = row[LocationScoreTable.score],
    )
}
