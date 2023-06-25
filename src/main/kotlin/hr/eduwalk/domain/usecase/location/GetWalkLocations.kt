package hr.eduwalk.domain.usecase.location

import hr.eduwalk.domain.interfaces.ILocationDao
import hr.eduwalk.domain.model.WalkLocationsResponse
import hr.eduwalk.domain.model.toWalkLocationsResponse

class GetWalkLocations(
    private val locationDao: ILocationDao,
) {
    suspend operator fun invoke(walkId: String): WalkLocationsResponse =
        locationDao.getWalkLocations(walkId = walkId).toWalkLocationsResponse()
}
