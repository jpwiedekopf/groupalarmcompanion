package net.wiedekopf.ktor

import io.ktor.server.application.*

@Suppress("UnusedReceiverParameter")
fun Application.configureSecurity() {
    // TODO: setup security with the API Key setup
    // header: *Personal-Access-Token*
}

const val PAT_HEADER = "Personal-Access-Token"