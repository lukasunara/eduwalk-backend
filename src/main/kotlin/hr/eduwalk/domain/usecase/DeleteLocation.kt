package hr.eduwalk.domain.usecase

import hr.eduwalk.domain.interfaces.ILocationDao
import hr.eduwalk.domain.model.EmptyResponse
import hr.eduwalk.domain.model.toEmptyResponse

class DeleteLocation(
    private val locationDao: ILocationDao,
) {
    suspend operator fun invoke(locationId: Int): EmptyResponse =
        locationDao.deleteLocation(locationId = locationId).toEmptyResponse()
}
