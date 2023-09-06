package hr.eduwalk.domain.usecase.location

import hr.eduwalk.data.model.Location
import hr.eduwalk.domain.interfaces.ILocationDao
import hr.eduwalk.domain.model.LocationResponse
import hr.eduwalk.domain.model.ServiceResult
import hr.eduwalk.domain.model.toLocationResponse

class UpdateOrInsertLocation(
    private val locationDao: ILocationDao,
) {
    suspend operator fun invoke(location: Location): LocationResponse {
        locationDao.updateLocation(location = location).also { result ->
            if (result is ServiceResult.Success && result.data) {
                return ServiceResult.Success(data = location).toLocationResponse()
            }
        }
        return locationDao.insertLocation(location = location).toLocationResponse()
    }
}
