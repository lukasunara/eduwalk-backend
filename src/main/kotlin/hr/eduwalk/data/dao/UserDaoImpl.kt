package hr.eduwalk.data.dao

import hr.eduwalk.data.database.DatabaseFactory.dbQuery
import hr.eduwalk.data.database.table.UsersTable
import hr.eduwalk.data.model.User
import hr.eduwalk.domain.interfaces.IUserDao
import hr.eduwalk.domain.model.ErrorCode
import hr.eduwalk.domain.model.ServiceResult
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.sqlite.SQLiteErrorCode

class UserDaoImpl : IUserDao {

    override suspend fun insertUser(user: User): ServiceResult<User> = try {
        dbQuery {
            UsersTable.insert {
                it[username] = user.username
                it[role] = user.role
            }.resultedValues?.singleOrNull()?.let {
                ServiceResult.Success(resultRowToUser(it))
            } ?: ServiceResult.Error(ErrorCode.DATABASE_ERROR)
        }
    } catch (e: Exception) {
        when (e) {
            is ExposedSQLException -> {
                if (e.errorCode == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                    ServiceResult.Error(ErrorCode.USER_EXISTS)
                } else {
                    ServiceResult.Error(ErrorCode.DATABASE_ERROR)
                }
            }
            else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
        }
    }

    override suspend fun getUserByUsername(username: String): ServiceResult<User> = try {
        val dbUser = dbQuery {
            UsersTable.select { UsersTable.username eq username }.map(::resultRowToUser).single()
        }
        ServiceResult.Success(dbUser)
    } catch (e: Exception) {
        when (e) {
            is NoSuchElementException -> {
                ServiceResult.Error(ErrorCode.UNKNOWN_USER)
            }
            is ExposedSQLException -> {
                println("Exception from getUserByUsername(): ${e.errorCode}")
                ServiceResult.Error(ErrorCode.DATABASE_ERROR)
            }
            else -> ServiceResult.Error(ErrorCode.DATABASE_ERROR)
        }
    }

    private fun resultRowToUser(row: ResultRow) = User(
        username = row[UsersTable.username],
        role = row[UsersTable.role],
    )
}
