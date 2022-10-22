package net.wiedekopf.groupalarmcompanion.model

import java.net.URI

data class UserDetails(
    val id: String,
    val email: String,
    val name: String,
    val surname: String,
    val avatarUrl: URI? = null,
    val active: Boolean,
) {
    val nameFormat get() = "${name.trim()} ${surname.trim()}"
}