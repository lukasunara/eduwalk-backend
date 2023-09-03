package hr.eduwalk.domain.interfaces

import hr.eduwalk.data.model.LocationWithScore
import hr.eduwalk.data.model.WalkWithScore
import hr.eduwalk.domain.model.ServiceResult

interface IJoinDao {

    suspend fun getWalkLocationsWithScores(walkId: String, userId: String): ServiceResult<List<LocationWithScore>>
    suspend fun getWalksWithScores(userId: String): ServiceResult<List<WalkWithScore>>
}
