package gacompanion.mockserver

import gacompanion.lib.models.LibraryModel
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import gacompanion.mockserver.ktor.configureSecurity
import net.wiedekopf.ktor.*
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("MockServerApp")

fun main() {
    logger.info("Starting Mockserver, using shared library version ${LibraryModel.VERSION}")
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureMonitoring()
    configureHTTP()
    configureSerialization()
    configureSecurity()
    val dataService = DataService()
    configureRouting(dataService)
}
