package dev.syorito_hatsuki.onlyone.api.user.get_users.response

import kotlinx.serialization.Serializable

@Serializable
data class DialogMessage(
    val userid : Int,
    val username : String,
    val avatar : String,
    val timestamp : Long,
    val time : String,
    val text : String
)
