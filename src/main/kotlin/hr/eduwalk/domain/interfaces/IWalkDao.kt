package hr.eduwalk.domain.interfaces

import hr.eduwalk.data.model.Walk
import hr.eduwalk.domain.model.ServiceResult

interface IWalkDao {
    suspend fun insertWalk(walk: Walk): ServiceResult<Unit>
    suspend fun updateWalk(walkId: String, title: String, description: String?): ServiceResult<Unit>
    suspend fun deleteWalk(walkId: String): ServiceResult<Unit>
    suspend fun getWalkById(walkId: String): ServiceResult<Walk>
    suspend fun getDefaultWalks(): ServiceResult<List<Walk>>
    suspend fun getUserCreatedWalks(userId: String): ServiceResult<List<Walk>>
}
