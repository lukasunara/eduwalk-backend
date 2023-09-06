package hr.eduwalk.domain.usecase.location

import hr.eduwalk.domain.interfaces.ILocationDao
import hr.eduwalk.domain.model.LocationResponse
import hr.eduwalk.domain.model.toLocationResponse

class GetLocation(
    private val locationDao: ILocationDao,
) {
    suspend operator fun invoke(locationId: Long): LocationResponse =
        locationDao.getLocationById(locationId = locationId).toLocationResponse()
}
