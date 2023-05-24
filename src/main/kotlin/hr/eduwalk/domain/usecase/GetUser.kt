package hr.eduwalk.domain.usecase

import hr.eduwalk.domain.interfaces.IUserDao
import hr.eduwalk.domain.model.ResponseError
import hr.eduwalk.domain.model.ServiceResult
import hr.eduwalk.domain.model.UserResponse

class GetUser(
    private val userDao: IUserDao,
) {
    suspend operator fun invoke(username: String): UserResponse =
        when (val dbResult = userDao.getUserByUsername(username = username)) {
            is ServiceResult.Success -> UserResponse(user = dbResult.data)
            is ServiceResult.Error -> UserResponse(
                errors = listOf(ResponseError(code = dbResult.error, message = dbResult.error.message))
            )
        }
}
