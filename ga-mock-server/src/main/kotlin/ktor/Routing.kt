package net.wiedekopf.ktor

import io.ktor.server.application.*
import io.ktor.server.routing.*
import net.wiedekopf.mock.apiRouting

fun Application.configureRouting() {
    routing {
        apiRouting()
    }
}
