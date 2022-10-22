package net.wiedekopf.groupalarmcompanion.model

import java.net.URI

data class OrganizationList(
    val totalOrganizations: Int,
    val organizations: List<Organization>
)

data class Organization(
    val id: String,
    val parentId: String? = null,
    val name: String,
    val description: String? = null,
    val state: String,
    val location: Location? = null,
    val avatarURL: URI? = null,
    val timezone: String
) {
    val isActive get() = state == "active"
    data class Location(
        val address: String? = null,
        val latitude: Double? = null,
        val longitude: Double? = null
    )
}