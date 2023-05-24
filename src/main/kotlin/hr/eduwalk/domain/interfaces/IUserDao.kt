package hr.eduwalk.domain.interfaces

import hr.eduwalk.data.model.User
import hr.eduwalk.domain.model.ServiceResult

interface IUserDao {
    suspend fun insertUser(user: User): ServiceResult<User>
    suspend fun getUserByUsername(username: String): ServiceResult<User>
}
