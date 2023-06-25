package hr.eduwalk.domain.usecase.walkscore

import hr.eduwalk.domain.interfaces.IWalkScoreDao
import hr.eduwalk.domain.model.WalkScoreResponse
import hr.eduwalk.domain.model.toWalkScoreResponse

class GetWalkScoreForUser(
    private val walkScoreDao: IWalkScoreDao,
) {
    suspend operator fun invoke(walkId: String, userId: String): WalkScoreResponse =
        walkScoreDao.getWalkScoreForUser(walkId = walkId, userId = userId).toWalkScoreResponse()
}
