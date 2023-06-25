package hr.eduwalk.domain.usecase.user

import hr.eduwalk.domain.interfaces.IUserDao
import hr.eduwalk.domain.model.UserResponse
import hr.eduwalk.domain.model.toUserResponse

class GetUser(
    private val userDao: IUserDao,
) {
    suspend operator fun invoke(username: String): UserResponse =
        userDao.getUserByUsername(username = username).toUserResponse()
}
