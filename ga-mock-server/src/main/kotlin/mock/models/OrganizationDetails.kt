@file:Suppress("unused")

package net.wiedekopf.mock.models

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


object MockOrganization {
    const val ID = 4242
    const val NAME = "Test Organization"
    const val DESCRIPTION = "Test Organization Description"
    const val AVATAR_URL = "https://www.flaticon.com/free-icon/organization_7018508"
    const val TIMEZONE_ID = "Europe/Berlin"
    const val GLOBAL_ORG_ID = 0

    const val PERSONNEL_COUNT = 100
}