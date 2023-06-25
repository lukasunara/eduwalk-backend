package hr.eduwalk.domain.usecase.walk

import hr.eduwalk.domain.interfaces.IWalkDao
import hr.eduwalk.domain.model.WalkResponse
import hr.eduwalk.domain.model.toWalkResponse

class GetWalkById(
    private val walkDao: IWalkDao,
) {
    suspend operator fun invoke(walkId: String): WalkResponse = walkDao.getWalkById(walkId = walkId).toWalkResponse()
}
