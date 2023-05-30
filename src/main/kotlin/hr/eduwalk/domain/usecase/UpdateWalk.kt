package hr.eduwalk.domain.usecase

import hr.eduwalk.domain.interfaces.IWalkDao
import hr.eduwalk.domain.model.EmptyResponse
import hr.eduwalk.domain.model.UpdateWalkRequestBody
import hr.eduwalk.domain.model.toEmptyResponse

class UpdateWalk(
    private val walkDao: IWalkDao,
) {
    suspend operator fun invoke(walkId: String, updateWalkRequestBody: UpdateWalkRequestBody): EmptyResponse =
        walkDao.updateWalk(
            walkId = walkId,
            title = updateWalkRequestBody.title,
            description = updateWalkRequestBody.description,
        ).toEmptyResponse()
}
