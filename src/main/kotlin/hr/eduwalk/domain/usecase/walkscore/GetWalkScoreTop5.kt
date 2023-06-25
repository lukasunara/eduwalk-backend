package hr.eduwalk.domain.usecase.walkscore

import hr.eduwalk.domain.interfaces.IWalkScoreDao
import hr.eduwalk.domain.model.WalkScoreTop5Response
import hr.eduwalk.domain.model.toWalkScoreTop5Response

class GetWalkScoreTop5(
    private val walkScoreDao: IWalkScoreDao,
) {
    suspend operator fun invoke(walkId: String): WalkScoreTop5Response =
        walkScoreDao.getWalkScoreTop5(walkId = walkId).toWalkScoreTop5Response()
}
