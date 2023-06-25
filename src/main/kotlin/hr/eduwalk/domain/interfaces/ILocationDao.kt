package hr.eduwalk.domain.interfaces

import hr.eduwalk.data.model.Location
import hr.eduwalk.domain.model.ServiceResult

interface ILocationDao {
    suspend fun insertLocation(location: Location): ServiceResult<Unit>
    suspend fun updateLocation(location: Location): ServiceResult<Boolean>
    suspend fun deleteLocation(locationId: Int): ServiceResult<Unit>
    suspend fun getWalkLocations(walkId: String): ServiceResult<List<Location>>
}
