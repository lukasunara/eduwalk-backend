package hr.eduwalk.domain.usecase

import hr.eduwalk.data.model.User
import hr.eduwalk.domain.interfaces.IUserDao
import hr.eduwalk.domain.model.ResponseError
import hr.eduwalk.domain.model.ServiceResult
import hr.eduwalk.domain.model.UserResponse

class GetOrInsertUser(
    private val userDao: IUserDao,
) {
    suspend operator fun invoke(user: User): UserResponse {
        userDao.getUserByUsername(username = user.username).also { existingUser ->
            if (existingUser is ServiceResult.Success) {
                return UserResponse(user = existingUser.data)
            }
        }
        return when (val newUser = userDao.insertUser(user = user)) {
            is ServiceResult.Success -> UserResponse(user = newUser.data)
            is ServiceResult.Error -> UserResponse(
                errors = listOf(ResponseError(code = newUser.error, message = newUser.error.message))
            )
        }
    }
}
