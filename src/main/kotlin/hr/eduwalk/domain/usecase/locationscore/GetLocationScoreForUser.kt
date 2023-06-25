package hr.eduwalk.domain.usecase.locationscore

import hr.eduwalk.domain.interfaces.ILocationScoreDao
import hr.eduwalk.domain.model.LocationScoreResponse
import hr.eduwalk.domain.model.toLocationScoreResponse

class GetLocationScoreForUser(
    private val locationScoreDao: ILocationScoreDao,
) {
    suspend operator fun invoke(locationId: Int, userId: String): LocationScoreResponse =
        locationScoreDao.getLocationScoreForUser(locationId = locationId, userId = userId).toLocationScoreResponse()
}
