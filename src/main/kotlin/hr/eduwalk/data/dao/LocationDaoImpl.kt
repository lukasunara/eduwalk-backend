package hr.eduwalk.data.dao

import hr.eduwalk.data.database.DatabaseFactory
import hr.eduwalk.data.database.table.LocationTable
import hr.eduwalk.data.model.Location
import hr.eduwalk.domain.interfaces.ILocationDao
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

class LocationDaoImpl : ILocationDao {

    override suspend fun getLocationById(locationId: Long): ServiceResult<Location> = try {
        val dbLocation = DatabaseFactory.dbQuery {
            LocationTable.select { LocationTable.id eq locationId }.map(::resultRowToLocation).single()
        }
        ServiceResult.Success(data = dbLocation)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is NoSuchElementException -> ErrorCode.UNKNOWN_LOCATION
            is ExposedSQLException -> {
                println("Exception from getLocationById(): ${e.errorCode}")
                ErrorCode.DATABASE_ERROR
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun insertLocation(location: Location): ServiceResult<Location> = try {
        DatabaseFactory.dbQuery {
            LocationTable.insert {
                it[latitude] = location.latitude
                it[longitude] = location.longitude
                it[title] = location.title
                it[description] = location.description
                it[imageBase64] = location.imageBase64
                it[thresholdDistance] = location.thresholdDistance
                it[walkId] = location.walkId
            }.resultedValues?.singleOrNull()?.let {
                ServiceResult.Success(data = resultRowToLocation(it))
            } ?: ServiceResult.Error(error = ResponseError(errorCode = ErrorCode.DATABASE_ERROR))
        }
    } catch (e: Exception) {
        val errorCode = when (e) {
            is ExposedSQLException -> {
                if (e.errorCode == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                    ErrorCode.LOCATION_EXISTS
                } else {
                    ErrorCode.DATABASE_ERROR
                }
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun updateLocation(location: Location): ServiceResult<Boolean> = try {
        location.id ?: throw IllegalArgumentException()

        val dbUpdateResult = DatabaseFactory.dbQuery {
            LocationTable.update(
                where = { LocationTable.id eq location.id },
                body = {
                    it[latitude] = location.latitude
                    it[longitude] = location.longitude
                    it[title] = location.title
                    it[description] = location.description
                    it[imageBase64] = location.imageBase64
                    it[thresholdDistance] = location.thresholdDistance
                }
            ) > 0
        }
        ServiceResult.Success(data = dbUpdateResult)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is NoSuchElementException -> ErrorCode.UNKNOWN_LOCATION
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun deleteLocation(locationId: Long): ServiceResult<Unit> = try {
        val dbDeleteResult = DatabaseFactory.dbQuery {
            LocationTable.deleteWhere { LocationTable.id eq locationId }
        }
        if (dbDeleteResult == 0) throw RuntimeException()
        ServiceResult.Success(data = Unit)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is RuntimeException -> ErrorCode.UNKNOWN_LOCATION
            is ExposedSQLException -> {
                println("Exception from deleteLocation(): ${e.errorCode}")
                ErrorCode.DATABASE_ERROR
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun getWalkLocations(walkId: String): ServiceResult<List<Location>> = try {
        val dbLocations = DatabaseFactory.dbQuery {
            LocationTable.select { LocationTable.walkId eq walkId }.map(::resultRowToLocation)
        }
        ServiceResult.Success(data = dbLocations)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is ExposedSQLException -> {
                println("Exception from getWalkLocations(): ${e.errorCode}")
                ErrorCode.DATABASE_ERROR
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    private fun resultRowToLocation(row: ResultRow) = Location(
        id = row[LocationTable.id],
        latitude = row[LocationTable.latitude],
        longitude = row[LocationTable.longitude],
        title = row[LocationTable.title],
        description = row[LocationTable.description],
        imageBase64 = row[LocationTable.imageBase64],
        thresholdDistance = row[LocationTable.thresholdDistance],
        walkId = row[LocationTable.walkId],
    )
}
