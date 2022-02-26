package dev.syorito_hatsuki.onlyone.api.user.get_users.response

import kotlinx.serialization.Serializable

@Serializable
data class GetUser(
    val friend_status: FriendStatus,
    val friends: Friends,
    val user: User
)
@Serializable
data class FriendStatus(
    val forme: Boolean,
    val status: Int
)
@Serializable
data class Friends(
    val count: Int,
    val list: List<UserPage>
)
@Serializable
data class UserPage(
    val forme: Boolean,
    val status: Int,
    val timestamp: Int,
    val user: User,
    val user2: Int,
    val userid: Int
)