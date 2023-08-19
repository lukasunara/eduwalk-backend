package hr.eduwalk.domain.usecase.join

import hr.eduwalk.domain.interfaces.IJoinDao
import hr.eduwalk.domain.model.LocationsWithScoresResponse
import hr.eduwalk.domain.model.toLocationsWithScoresResponse

class GetLocationsWithScores(
    private val joinDao: IJoinDao,
) {
    suspend operator fun invoke(walkId: String, userId: String): LocationsWithScoresResponse =
        joinDao.getWalkLocationsWithScores(walkId = walkId, userId = userId).toLocationsWithScoresResponse()
}
