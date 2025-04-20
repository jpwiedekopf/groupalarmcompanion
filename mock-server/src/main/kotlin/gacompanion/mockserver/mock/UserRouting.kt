package gacompanion.mockserver.mock

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import gacompanion.mockserver.DataService
import gacompanion.mockserver.mock.data.MockOrganization
import gacompanion.mockserver.mock.data.MockUser
import gacompanion.lib.models.Availability
import gacompanion.lib.models.UserDetails

fun Route.userRouting(dataService: DataService) {
    userDetailsRoute(dataService)
    userAvailabilityRoute(dataService)
}

fun Route.userAvailabilityRoute(dataService: DataService) {
    route("/user/availability") {
        get {
            val globalAvailability = Availability(
                userID = MockUser.USER_ID,
                organizationID = MockOrganization.GLOBAL_ORG_ID,
                preference = dataService.user.isGloballyAvailable
            )
            val orgAvailability = when (dataService.user.isOrganizationAvailable) {
                false -> Availability(
                    userID = MockUser.USER_ID,
                    organizationID = MockOrganization.ID,
                    preference = false
                )

                true -> null
            }
            call.respond(listOfNotNull(globalAvailability, orgAvailability))
        }
        put {
            val payload = call.receive<Availability>()
            when (payload.organizationID) {
                MockOrganization.GLOBAL_ORG_ID -> {
                    dataService.user.isGloballyAvailable = payload.preference
                    call.application.log.info("Set global availability to ${payload.preference}")
                }

                MockOrganization.ID -> {
                    dataService.user.isOrganizationAvailable = payload.preference
                    call.application.log.info("Set organization availability to ${payload.preference}")
                }

                else -> {
                    call.respond(
                        HttpStatusCode.BadRequest, ErrorResponse(
                            message = "Sie sind kein Teilnehmer der Organisation.",
                            error = "not a member of requested organization"
                        )
                    )
                    return@put
                }
            }
            call.respond(SuccessResponse())
        }
    }

}

@Serializable
data class SuccessResponse(
    val success: Boolean = true,
    val message: String = "Eintrag erfolgreich gespeichert",
    val i18nKey: String = "MSG_ENTRY_SAVED_SUCCESSFULLY"
)

@Serializable
data class ErrorResponse(
    val success: Boolean = false,
    val message: String,
    val error: String
)

fun Route.userDetailsRoute(dataService: DataService) {
    get("/user") {
        val mockUser = UserDetails(
            id = MockUser.USER_ID,
            email = MockUser.EMAIL,
            name = MockUser.NAME,
            surname = MockUser.SURNAME,
            avatarURL = MockUser.AVATAR_URL,
            availablePreference = dataService.user.isGloballyAvailable
        )
        call.respond(mockUser)
    }
}