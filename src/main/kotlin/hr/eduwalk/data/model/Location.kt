package hr.eduwalk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val id: Int,
    val latitude: String,
    val longitude: String,
    val title: String,
    val description: String?,
    val imageBase64: String?,
    val thresholdDistance: Int = DEFAULT_THRESHOLD_DISTANCE,
    val walkId: String,
) {
    companion object {
        const val DEFAULT_THRESHOLD_DISTANCE = 20
    }
}
