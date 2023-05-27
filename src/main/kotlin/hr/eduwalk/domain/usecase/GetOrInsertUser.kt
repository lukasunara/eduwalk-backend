package hr.eduwalk.domain.usecase

import hr.eduwalk.data.model.User
import hr.eduwalk.domain.interfaces.IUserDao
import hr.eduwalk.domain.model.ServiceResult
import hr.eduwalk.domain.model.UserResponse
import hr.eduwalk.domain.model.toUserResponse

class GetOrInsertUser(
    private val userDao: IUserDao,
) {
    suspend operator fun invoke(user: User): UserResponse {
        userDao.getUserByUsername(username = user.username).also { existingUser ->
            if (existingUser is ServiceResult.Success) {
                return UserResponse(user = existingUser.data)
            }
        }
        return userDao.insertUser(user = user).toUserResponse()
    }
}
