@file:Suppress("unused")

package gacompanion.lib.models

import kotlinx.serialization.Serializable

@Serializable
data class OrganizationDetails(
    val id: Int,
    val name: String,
    val description: String,
    val avatarURL: String,
    val timezone: String
)

@Serializable
data class OrganizationAvailability(
    val countInRoot: CountList,
    val countTotal: CountList
) {
    @Serializable
    data class CountList(
        val countInEvent: Int,
        val countAvailable: Int,
        val countNotAvailable: Int
    )
}
