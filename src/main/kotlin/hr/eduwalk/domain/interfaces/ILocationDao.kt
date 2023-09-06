package hr.eduwalk.domain.interfaces

import hr.eduwalk.data.model.Location
import hr.eduwalk.domain.model.ServiceResult

interface ILocationDao {
    suspend fun getLocationById(locationId: Long): ServiceResult<Location>
    suspend fun insertLocation(location: Location): ServiceResult<Location>
    suspend fun updateLocation(location: Location): ServiceResult<Boolean>
    suspend fun deleteLocation(locationId: Long): ServiceResult<Unit>
    suspend fun getWalkLocations(walkId: String): ServiceResult<List<Location>>
}
