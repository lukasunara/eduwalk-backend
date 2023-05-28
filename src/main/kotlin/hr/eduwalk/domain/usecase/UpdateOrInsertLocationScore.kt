package hr.eduwalk.domain.usecase

import hr.eduwalk.data.model.LocationScore
import hr.eduwalk.domain.interfaces.ILocationScoreDao
import hr.eduwalk.domain.model.ServiceResult

class UpdateOrInsertLocationScore(
    private val locationScoreDao: ILocationScoreDao,
) {
    suspend operator fun invoke(locationScore: LocationScore): ServiceResult<Unit> {
        locationScoreDao.updateLocationScore(locationScore = locationScore).also { result ->
            if (result is ServiceResult.Success && result.data) {
                return ServiceResult.Success(data = Unit)
            }
        }
        return locationScoreDao.insertLocationScore(locationScore = locationScore)
    }
}
