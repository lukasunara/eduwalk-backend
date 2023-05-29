package hr.eduwalk.domain.usecase

import hr.eduwalk.data.model.Walk
import hr.eduwalk.domain.interfaces.IWalkDao
import hr.eduwalk.domain.model.ServiceResult

class UpdateOrInsertWalk(
    private val walkDao: IWalkDao,
) {
    suspend operator fun invoke(walk: Walk): ServiceResult<Unit> {
        walkDao.updateWalk(walk = walk).also { result ->
            if (result is ServiceResult.Success && result.data) {
                return ServiceResult.Success(data = Unit)
            }
        }
        return walkDao.insertWalk(walk = walk)
    }
}
