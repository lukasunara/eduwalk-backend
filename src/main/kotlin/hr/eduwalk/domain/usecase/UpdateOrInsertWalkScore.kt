package hr.eduwalk.domain.usecase

import hr.eduwalk.data.model.WalkScore
import hr.eduwalk.domain.interfaces.IWalkScoreDao
import hr.eduwalk.domain.model.ServiceResult

class UpdateOrInsertWalkScore(
    private val walkScoreDao: IWalkScoreDao,
) {
    suspend operator fun invoke(walkScore: WalkScore): ServiceResult<Unit> {
        walkScoreDao.updateWalkScore(walkScore = walkScore).also { result ->
            if (result is ServiceResult.Success && result.data) {
                return ServiceResult.Success(data = Unit)
            }
        }
        return walkScoreDao.insertWalkScore(walkScore = walkScore)
    }
}
