package hr.eduwalk.domain.usecase.locationscore

import hr.eduwalk.data.model.LocationScore
import hr.eduwalk.domain.interfaces.ILocationScoreDao
import hr.eduwalk.domain.model.EmptyResponse
import hr.eduwalk.domain.model.ServiceResult
import hr.eduwalk.domain.model.toEmptyResponse

class UpdateOrInsertLocationScore(
    private val locationScoreDao: ILocationScoreDao,
) {
    suspend operator fun invoke(locationScore: LocationScore): EmptyResponse {
        locationScoreDao.updateLocationScore(locationScore = locationScore).also { result ->
            if (result is ServiceResult.Success && result.data) {
                return ServiceResult.Success(data = Unit).toEmptyResponse()
            }
        }
        return locationScoreDao.insertLocationScore(locationScore = locationScore).toEmptyResponse()
    }
}
