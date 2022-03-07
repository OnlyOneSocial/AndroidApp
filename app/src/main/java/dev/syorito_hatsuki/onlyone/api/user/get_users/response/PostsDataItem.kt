package dev.syorito_hatsuki.onlyone.api.user.get_users.response

import kotlinx.serialization.Serializable

@Serializable
data class PostsDataItem(
    val Image: String? = null,
    val Timestamp: Int,
    val answercount: Int,
    val answerto: String,
    val author: Int,
    val author_avatar: String,
    val author_username: String,
    val id: Int,
    val likes: List<Int>? = null,
    val random_id: String,
    val text: String,
    val time: String
)