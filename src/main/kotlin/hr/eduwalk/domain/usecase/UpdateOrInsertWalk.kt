package hr.eduwalk.domain.usecase

import hr.eduwalk.data.model.Walk
import hr.eduwalk.domain.interfaces.IWalkDao
import hr.eduwalk.domain.model.EmptyResponse
import hr.eduwalk.domain.model.ServiceResult
import hr.eduwalk.domain.model.toEmptyResponse

class UpdateOrInsertWalk(
    private val walkDao: IWalkDao,
) {
    suspend operator fun invoke(walk: Walk): EmptyResponse {
        walkDao.updateWalk(walk = walk).also { result ->
            if (result is ServiceResult.Success && result.data) {
                return ServiceResult.Success(data = Unit).toEmptyResponse()
            }
        }
        return walkDao.insertWalk(walk = walk).toEmptyResponse()
    }
}
