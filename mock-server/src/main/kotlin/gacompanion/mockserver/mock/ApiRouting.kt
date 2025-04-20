package gacompanion.mockserver.mock

import io.ktor.server.auth.*
import io.ktor.server.routing.*
import gacompanion.mockserver.DataService
import gacompanion.mockserver.ktor.AUTH_SCHEME

fun Route.apiRouting(dataService: DataService) {
    authenticate(AUTH_SCHEME) {
        route("/api/v1") {
            organizationRouting(dataService)
            userRouting(dataService)
        }
    }
}