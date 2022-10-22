package net.wiedekopf.groupalarmcompanion.shared.client

import android.content.Context
import kotlinx.coroutines.flow.first
import net.wiedekopf.groupalarmcompanion.shared.model.Availability
import net.wiedekopf.groupalarmcompanion.shared.model.Organization
import net.wiedekopf.groupalarmcompanion.shared.model.OrganizationList
import net.wiedekopf.groupalarmcompanion.shared.model.UserDetails
import java.net.URI
import kotlin.random.Random

class MockClient(
    randomAvailability: Boolean = false, context: Context
) : GroupAlarmClient(context) {

    private val availabilityMap = mutableMapOf(
        "0" to when (randomAvailability) {
            true -> Random.nextBoolean()
            else -> true
        }
    )

    private fun getGlobalAvailability() = availabilityMap["0"]!!

    override suspend fun areStoredCredentialsPresentAndValid(): Boolean =
        areCredentialsPresentAndValid(
            endpoint = settings.endpointFlow.first(), pat = settings.personalAccessTokenFlow.first()
        )

    override suspend fun areCredentialsPresentAndValid(endpoint: String?, pat: String?): Boolean =
        when {
            endpoint.isNullOrBlank() || pat.isNullOrBlank() -> false
            !endpoint.isUri() -> false
            pat.length < 32 -> false
            else -> true
        }

    override fun getCurrentOrganizationList() = OrganizationList(
        totalOrganizations = 1, organizations = listOf(
            Organization(
                id = "9809",
                parentId = "9768",
                name = "THW Lübeck",
                description = "THW Lübeck",
                state = "active",
                location = Organization.Location(
                    address = "Vorwerker Str. 141, Lübeck, Deutschland",
                    latitude = 53.9008747,
                    longitude = 10.674407
                ),
                avatarURL = URI.create("https://storage.googleapis.com/groupalarm-avatars-organization-production/avatar-9809-4os28iTa.png"),
                timezone = "Europe/Berlin"
            )
        )
    )

    override fun getUser() = UserDetails(
        id = "123456",
        email = "heinrich.helferlein@thw.de",
        name = "Heinrich ",
        surname = "Helferlein ",
        avatarUrl = URI.create("https://storage.googleapis.com/groupalarm-avatars-production/avatar-156510-tOUs363e.png"),
        active = true
    )

    override fun getAvailabilities(): List<Availability> =
        availabilityMap.map { (orgId, isAvailable) ->
            Availability(getUser().id, organizationId = orgId, preference = isAvailable)
        }

    override fun setGlobalAvailability(isAvailable: Boolean) {
        availabilityMap["0"] = isAvailable
    }

    override fun setOrgAvailability(isAvailable: Boolean, orgId: String) {
        assert(orgId != "0")
        when {
            getGlobalAvailability() && isAvailable -> availabilityMap.remove(orgId)
            !getGlobalAvailability() && isAvailable -> availabilityMap[orgId] = true
            getGlobalAvailability() && !isAvailable -> availabilityMap[orgId] = false
            else -> throw UnsupportedOperationException("Both the global availability and the organization availability are false, this is undefined behaviour")
        }
    }

    @Suppress("unused")
    private fun randomizeAvailability(): List<Availability> {
        val userId = getUser().id
        val orgId = getOrganizationList().organizations.first().id
        return when (Random.nextBoolean()) {
            true -> {
                val orgAvailability = Random.nextBoolean()
                listOf(
                    Availability(
                        userId = userId, organizationId = "0", preference = orgAvailability
                    ), Availability(
                        userId = userId, organizationId = orgId, preference = !orgAvailability
                    )
                )
            }
            else -> listOf(
                Availability(
                    userId = userId, organizationId = "0", preference = Random.nextBoolean()
                )
            )
        }
    }


}

private fun String.isUri(): Boolean =
    try {
        URI.create(this)
        true
    } catch (e: Exception) {
        false
    }