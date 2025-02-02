package net.wiedekopf.mock

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.wiedekopf.DataService
import net.wiedekopf.mock.models.OrganizationAvailability
import net.wiedekopf.mock.models.MockOrganization
import net.wiedekopf.mock.models.OrganizationDetails

fun Route.organizationRouting(dataService: DataService) {

    route("organizations") {
        val theOrganization = OrganizationDetails(
            id = MockOrganization.ID,
            name = MockOrganization.NAME,
            description = MockOrganization.DESCRIPTION,
            avatarURL = MockOrganization.AVATAR_URL,
            timezone = MockOrganization.TIMEZONE_ID,
        )
        getOrganizationList(theOrganization)
    }

    route("organization") {
        getAvailability(dataService)
    }
}

fun Route.getAvailability(dataService: DataService) {
    get("/{orgId}/availability") {
        val orgId = call.parameters["orgId"]
        when (orgId?.toInt()) {
            null -> call.respond(HttpStatusCode.BadRequest)
            MockOrganization.ID -> {
                val counts = dataService.orgAvailability.getCounts()
                val response = OrganizationAvailability(
                    countInRoot = counts,
                    countTotal = counts
                )
                call.respond(response)
            }
            else -> call.respond(HttpStatusCode.Forbidden)
        }
    }
}

fun Route.getOrganizationList(theOrganization: OrganizationDetails) {
    get {
        val organizationList = listOf(theOrganization)
        call.respond(organizationList)
    }
}
