package hr.eduwalk.domain.usecase

import hr.eduwalk.data.model.WalkScore
import hr.eduwalk.domain.interfaces.IWalkScoreDao
import hr.eduwalk.domain.model.EmptyResponse
import hr.eduwalk.domain.model.ServiceResult
import hr.eduwalk.domain.model.toEmptyResponse

class UpdateOrInsertWalkScore(
    private val walkScoreDao: IWalkScoreDao,
) {
    suspend operator fun invoke(walkScore: WalkScore): EmptyResponse {
        walkScoreDao.updateWalkScore(walkScore = walkScore).also { result ->
            if (result is ServiceResult.Success && result.data) {
                return ServiceResult.Success(data = Unit).toEmptyResponse()
            }
        }
        return walkScoreDao.insertWalkScore(walkScore = walkScore).toEmptyResponse()
    }
}
