package hr.eduwalk.domain.interfaces

import hr.eduwalk.data.model.LocationScore
import hr.eduwalk.domain.model.ServiceResult

interface ILocationScoreDao {
    suspend fun insertLocationScore(locationScore: LocationScore): ServiceResult<Unit>
    suspend fun updateLocationScore(locationScore: LocationScore): ServiceResult<Boolean>
    suspend fun getLocationScoreForUser(locationId: Int, userId: String): ServiceResult<LocationScore>
}
