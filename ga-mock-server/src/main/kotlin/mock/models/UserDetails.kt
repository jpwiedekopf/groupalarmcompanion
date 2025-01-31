package net.wiedekopf.mock.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDetails(
    val id: Int,
    val email: String,
    val name: String,
    val surname: String,
    val avatarURL: String,
    val availablePreference: Boolean
)