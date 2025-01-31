package net.wiedekopf.mock

import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.wiedekopf.mock.models.OrganizationConstants
import net.wiedekopf.mock.models.OrganizationDetails

fun Route.organizationRouting() {

    route("organizations") {
        val theOrganization = OrganizationDetails(
            id = OrganizationConstants.ID,
            name = OrganizationConstants.NAME,
            description = OrganizationConstants.DESCRIPTION,
            avatarURL = OrganizationConstants.AVATAR_URL,
            timezone = OrganizationConstants.TIMEZONE_ID,
        )
        getOrganizationList(theOrganization)
    }

}

fun Route.getOrganizationList(theOrganization: OrganizationDetails) {
    get {
        val organizationList = listOf(theOrganization)
        call.respond(organizationList)
    }
}
