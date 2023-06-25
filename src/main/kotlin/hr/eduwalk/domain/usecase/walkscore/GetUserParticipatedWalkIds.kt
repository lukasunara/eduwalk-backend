package hr.eduwalk.domain.usecase.walkscore

import hr.eduwalk.domain.interfaces.IWalkScoreDao
import hr.eduwalk.domain.model.WalkIdsResponse
import hr.eduwalk.domain.model.toWalkIdsResponse

class GetUserParticipatedWalkIds(
    private val walkScoreDao: IWalkScoreDao,
) {
    suspend operator fun invoke(userId: String): WalkIdsResponse =
        walkScoreDao.getUserParticipatedWalkIds(userId = userId).toWalkIdsResponse()
}
