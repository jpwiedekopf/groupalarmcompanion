@file:Suppress("unused")

package gacompanion.lib.models

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class RecurringAvailability(
    val entries: List<RecurringAvailabilityChange>,
    val total: Int
) {

    @Serializable
    data class RecurringAvailabilityChange(
        val id: Int,
        val userID: Int,
        val organizationID: Int,
        val active: Boolean,
        val changeDay: Int,
        val changeTime: LocalTime,
        val changeStatus: ChangeStatus
    )
}

object DayAsIntSerializer : KSerializer<Day> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.wiedekopf.mock.models.Day", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Day) {
        encoder.encodeInt(value.id)
    }

    override fun deserialize(decoder: Decoder): Day {
        val dayId = decoder.decodeInt()
        return Day.entries.find { it.id == dayId } ?: throw SerializationException("Invalid Day ID $dayId")
    }
}

@Serializable(with = DayAsIntSerializer::class)
enum class Day(val id: Int) {
    Monday(1),
    Tuesday(2),
    Wednesday(3),
    Thursday(4),
    Friday(5),
    Saturday(6),
    Sunday(7)
}

@Serializable
data class ScheduledAvailability(
    val entries: List<ScheduledAvailabilityChange>,
    val total: Int
) {

    @Serializable
    data class ScheduledAvailabilityChange(
        val id: Int,
        val userId: Int,
        val changeTime: LocalDateTime,
        val changeStatus: ChangeStatus,
    )
}

@Serializable(with = ChangeStatusLowerCaseSerializer::class)
enum class ChangeStatus {
    Unavailable,
    Available,
}


object ChangeStatusLowerCaseSerializer : KSerializer<ChangeStatus> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("net.wiedekopf.mock.models.ChangeStatus", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ChangeStatus) {
        encoder.encodeString(value.name.lowercase())
    }

    override fun deserialize(decoder: Decoder): ChangeStatus {
        val stringValue = decoder.decodeString()
        return ChangeStatus.entries.find { it.name.lowercase() == stringValue.lowercase() }
            ?: throw SerializationException("Invalid Change Status '$stringValue'")
    }
}