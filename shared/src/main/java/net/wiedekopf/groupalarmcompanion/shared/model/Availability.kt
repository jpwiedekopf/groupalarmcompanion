package net.wiedekopf.groupalarmcompanion.shared.model

data class Availability(
    val userId: String,
    val organizationId: String,
    val preference: Boolean
) {
    val isGlobal get() = organizationId == "0"
}