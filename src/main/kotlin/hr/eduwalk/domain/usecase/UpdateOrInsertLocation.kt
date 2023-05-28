package hr.eduwalk.domain.usecase

import hr.eduwalk.data.model.Location
import hr.eduwalk.domain.interfaces.ILocationDao
import hr.eduwalk.domain.model.ServiceResult

class UpdateOrInsertLocation(
    private val locationDao: ILocationDao,
) {
    suspend operator fun invoke(location: Location): ServiceResult<Unit> {
        locationDao.updateLocation(location = location).also { result ->
            if (result is ServiceResult.Success && result.data) {
                return ServiceResult.Success(data = Unit)
            }
        }
        return locationDao.insertLocation(location = location)
    }
}
