package hr.eduwalk.domain.usecase

import hr.eduwalk.domain.interfaces.IWalkDao
import hr.eduwalk.domain.model.WalksResponse
import hr.eduwalk.domain.model.toWalksResponse

class GetUserCreatedWalks(
    private val walkDao: IWalkDao,
) {
    suspend operator fun invoke(userId: String): WalksResponse =
        walkDao.getUserCreatedWalks(userId = userId).toWalksResponse()
}
