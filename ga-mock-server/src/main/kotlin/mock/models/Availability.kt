package net.wiedekopf.mock.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Availability(
    val userID: Int,
    val organizationID: Int,
    val preference: Boolean
)

@Serializable
data class AvailabilityChange(
    @SerialName("user_id") val userID: Int,
    val duration: Long,
    @SerialName("start_time") val startTime: LocalDateTime
)