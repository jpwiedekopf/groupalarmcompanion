package net.wiedekopf.ktor

import io.ktor.server.application.*
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.*
import gacompanion.mockserver.DataService
import gacompanion.mockserver.mock.apiRouting

fun Application.configureRouting(dataService: DataService) {
    routing {
        route("/") {
            get {
                call.respondRedirect("/api/v1")
            }
        }
        apiRouting(dataService)
    }
}
