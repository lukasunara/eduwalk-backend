package hr.eduwalk.domain.interfaces

import hr.eduwalk.data.model.WalkScore
import hr.eduwalk.domain.model.ServiceResult

interface IWalkScoreDao {
    suspend fun insertWalkScore(walkScore: WalkScore): ServiceResult<Unit>
    suspend fun updateWalkScore(walkScore: WalkScore): ServiceResult<Boolean>
    suspend fun getWalkScoreForUser(walkId: String, userId: String): ServiceResult<WalkScore>
    suspend fun getWalkScoreTop5(walkId: String): ServiceResult<List<WalkScore>>
}
