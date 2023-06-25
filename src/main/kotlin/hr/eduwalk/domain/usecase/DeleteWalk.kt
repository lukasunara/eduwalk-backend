package hr.eduwalk.domain.usecase

import hr.eduwalk.domain.interfaces.IWalkDao
import hr.eduwalk.domain.model.EmptyResponse
import hr.eduwalk.domain.model.toEmptyResponse

class DeleteWalk(
    private val walkDao: IWalkDao,
) {
    suspend operator fun invoke(walkId: String): EmptyResponse =
        walkDao.deleteWalk(walkId = walkId).toEmptyResponse()
}
