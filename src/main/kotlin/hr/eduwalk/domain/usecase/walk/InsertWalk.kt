package hr.eduwalk.domain.usecase.walk

import hr.eduwalk.data.model.Walk
import hr.eduwalk.domain.interfaces.IWalkDao
import hr.eduwalk.domain.model.EmptyResponse
import hr.eduwalk.domain.model.toEmptyResponse

class InsertWalk(
    private val walkDao: IWalkDao,
) {
    suspend operator fun invoke(walk: Walk): EmptyResponse = walkDao.insertWalk(walk = walk).toEmptyResponse()
}
