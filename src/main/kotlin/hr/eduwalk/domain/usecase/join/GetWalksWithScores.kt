package hr.eduwalk.domain.usecase.join

import hr.eduwalk.domain.interfaces.IJoinDao
import hr.eduwalk.domain.model.WalksWithScoresResponse
import hr.eduwalk.domain.model.toWalksWithScoresResponse

class GetWalksWithScores(
    private val joinDao: IJoinDao,
) {
    suspend operator fun invoke(userId: String): WalksWithScoresResponse =
        joinDao.getWalksWithScores(userId = userId).toWalksWithScoresResponse()
}
