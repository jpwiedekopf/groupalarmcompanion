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


@Suppress("unused")
object MockUser {
    const val USER_ID = 142142
    const val EMAIL = "test@example.com"
    const val NAME = "First"
    const val SURNNAME = "Testname"
    const val AVATAR_URL = "https://cdn-icons-png.flaticon.com/512/3436/3436651.png"
}