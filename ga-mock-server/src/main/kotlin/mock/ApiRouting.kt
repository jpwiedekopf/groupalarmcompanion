package net.wiedekopf.mock

import io.ktor.server.routing.*

fun Route.apiRouting() {
    route("/api/v1") {
        organizationRouting()
        //userRouting()
    }
}