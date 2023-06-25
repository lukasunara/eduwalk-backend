package hr.eduwalk.domain.usecase

import hr.eduwalk.domain.interfaces.IUserDao
import hr.eduwalk.domain.model.EmptyResponse
import hr.eduwalk.domain.model.toEmptyResponse

class DeleteUser(
    private val userDao: IUserDao,
) {
    suspend operator fun invoke(username: String): EmptyResponse =
        userDao.deleteUser(username = username).toEmptyResponse()
}
