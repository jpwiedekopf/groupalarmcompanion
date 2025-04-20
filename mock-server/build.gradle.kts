@file:Suppress("PropertyName")

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "net.wiedekopf.gacompanion"
version = "0.0.1"

application {
    mainClass.set("net.wiedekopf.gacompanion.mockserver.MockServerApp")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.mock.ktor.server.call.logging)
    implementation(libs.mock.ktor.server.call.id)
    implementation(libs.mock.ktor.server.cors)
    implementation(libs.mock.ktor.server.core)
    implementation(libs.mock.ktor.server.host.common)
    implementation(libs.mock.ktor.server.status.pages)
    implementation(libs.mock.ktor.server.content.negotiation)
    implementation(libs.mock.ktor.serialization.kotlinx.json)
    implementation(libs.mock.ktor.server.auth)
    implementation(libs.mock.ktor.server.netty)
    implementation(libs.mock.logback.classic)
    implementation(libs.common.kotlinx.datetime)
    testImplementation(libs.mock.ktor.server.test.host)
    testImplementation(libs.mock.kotlin.test.junit)
    implementation(project(":shared-lib"))
}
