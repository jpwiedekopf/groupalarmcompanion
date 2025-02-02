package net.wiedekopf.ktor

import io.ktor.server.application.*
import io.ktor.server.routing.*
import net.wiedekopf.DataService
import net.wiedekopf.mock.apiRouting

fun Application.configureRouting(dataService: DataService) {
    routing {
        apiRouting(dataService)
    }
}
