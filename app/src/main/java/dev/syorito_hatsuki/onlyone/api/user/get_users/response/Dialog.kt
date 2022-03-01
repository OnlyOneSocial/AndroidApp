package dev.syorito_hatsuki.onlyone.api.user.get_users.response

import kotlinx.serialization.Serializable

@Serializable
data class Dialog(
    val userid: Int,
    val sendto: Int,
    val username: String,
    val avatar: String,
    val time: String,
    val timestamp: Long,
    val text: String
)