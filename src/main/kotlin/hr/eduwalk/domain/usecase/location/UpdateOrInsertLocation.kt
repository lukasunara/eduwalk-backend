package hr.eduwalk.domain.usecase.location

import hr.eduwalk.data.model.Location
import hr.eduwalk.domain.interfaces.ILocationDao
import hr.eduwalk.domain.model.EmptyResponse
import hr.eduwalk.domain.model.ServiceResult
import hr.eduwalk.domain.model.toEmptyResponse

class UpdateOrInsertLocation(
    private val locationDao: ILocationDao,
) {
    suspend operator fun invoke(location: Location): EmptyResponse {
        locationDao.updateLocation(location = location).also { result ->
            if (result is ServiceResult.Success && result.data) {
                return ServiceResult.Success(data = Unit).toEmptyResponse()
            }
        }
        return locationDao.insertLocation(location = location).toEmptyResponse()
    }
}
