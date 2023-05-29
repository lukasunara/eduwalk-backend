package hr.eduwalk.domain.interfaces

import hr.eduwalk.data.model.Walk
import hr.eduwalk.domain.model.ServiceResult

interface IWalkDao {
    suspend fun insertWalk(walk: Walk): ServiceResult<Unit>
    suspend fun updateWalk(walk: Walk): ServiceResult<Boolean>
    suspend fun getWalkById(walkId: String): ServiceResult<Walk>
    suspend fun getDefaultWalks(): ServiceResult<List<Walk>>
    suspend fun getUserCreatedWalks(userId: String): ServiceResult<List<Walk>>
}
