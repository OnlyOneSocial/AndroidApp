package dev.syorito_hatsuki.onlyone.api.user.get_users.response

import kotlinx.serialization.Serializable

@Serializable
data class UserAuth(
    val id:Int,
    val username: String,
    val avatar: String,
    val jwt: String
)
