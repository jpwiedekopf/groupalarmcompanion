package net.wiedekopf.mock

import io.ktor.server.auth.*
import io.ktor.server.routing.*
import net.wiedekopf.DataService
import net.wiedekopf.ktor.AUTH_SCHEME

fun Route.apiRouting(dataService: DataService) {
    authenticate(AUTH_SCHEME) {
        route("/api/v1") {
            organizationRouting(dataService)
            userRouting(dataService)
        }
    }
}