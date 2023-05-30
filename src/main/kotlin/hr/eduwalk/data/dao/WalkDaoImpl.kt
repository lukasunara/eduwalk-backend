package hr.eduwalk.data.dao

import hr.eduwalk.data.database.DatabaseFactory
import hr.eduwalk.data.database.table.WalkTable
import hr.eduwalk.data.model.Walk
import hr.eduwalk.domain.interfaces.IWalkDao
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

class WalkDaoImpl : IWalkDao {

    override suspend fun insertWalk(walk: Walk): ServiceResult<Unit> = try {
        DatabaseFactory.dbQuery {
            WalkTable.insert {
                it[id] = walk.id
                it[title] = walk.title
                it[description] = walk.description
                it[creatorId] = walk.creatorId
            }.resultedValues?.singleOrNull()?.let {
                ServiceResult.Success(data = Unit)
            } ?: ServiceResult.Error(error = ResponseError(errorCode = ErrorCode.DATABASE_ERROR))
        }
    } catch (e: Exception) {
        val errorCode = when (e) {
            is ExposedSQLException -> {
                if (e.errorCode == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                    ErrorCode.WALK_EXISTS
                } else {
                    ErrorCode.DATABASE_ERROR
                }
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun updateWalk(
        walkId: String,
        title: String,
        description: String?,
    ): ServiceResult<Unit> = try {
        val dbUpdateResult = DatabaseFactory.dbQuery {
            WalkTable.update(
                where = { WalkTable.id eq walkId },
                body = {
                    it[WalkTable.title] = title
                    it[WalkTable.description] = description
                }
            )
        }
        if (dbUpdateResult == 0) throw RuntimeException()
        ServiceResult.Success(data = Unit)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is NoSuchElementException -> ErrorCode.UNKNOWN_WALK
            is RuntimeException -> ErrorCode.UNKNOWN_WALK
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun getWalkById(walkId: String): ServiceResult<Walk> = try {
        val dbWalk = DatabaseFactory.dbQuery {
            WalkTable.select { WalkTable.id eq walkId }.map(::resultRowToWalk).single()
        }
        ServiceResult.Success(dbWalk)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is NoSuchElementException -> ErrorCode.UNKNOWN_WALK
            is ExposedSQLException -> {
                println("Exception from getWalkById(): ${e.errorCode}")
                ErrorCode.DATABASE_ERROR
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun getDefaultWalks(): ServiceResult<List<Walk>> = try {
        val dbWalks = DatabaseFactory.dbQuery {
            WalkTable
                .select { WalkTable.creatorId.isNull() }
                .orderBy(column = WalkTable.title, order = SortOrder.ASC)
                .map(::resultRowToWalk)
        }
        ServiceResult.Success(data = dbWalks)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is ExposedSQLException -> {
                println("Exception from getDefaultWalks(): ${e.errorCode}")
                ErrorCode.DATABASE_ERROR
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    override suspend fun getUserCreatedWalks(userId: String): ServiceResult<List<Walk>> = try {
        val dbWalks = DatabaseFactory.dbQuery {
            WalkTable
                .select{ WalkTable.creatorId eq userId }
                .orderBy(column = WalkTable.title, order = SortOrder.ASC)
                .map(::resultRowToWalk)
        }
        ServiceResult.Success(data = dbWalks)
    } catch (e: Exception) {
        val errorCode = when (e) {
            is ExposedSQLException -> {
                println("Exception from getUserCreatedWalks(): ${e.errorCode}")
                ErrorCode.DATABASE_ERROR
            }
            else -> ErrorCode.DATABASE_ERROR
        }
        ServiceResult.Error(error = ResponseError(errorCode = errorCode))
    }

    private fun resultRowToWalk(row: ResultRow) = Walk(
        id = row[WalkTable.id],
        title = row[WalkTable.title],
        description = row[WalkTable.description],
        creatorId = row[WalkTable.creatorId],
    )
}
