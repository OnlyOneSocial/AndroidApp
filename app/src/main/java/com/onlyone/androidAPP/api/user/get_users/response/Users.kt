package com.onlyone.androidAPP.api.user.get_users.response

import kotlinx.serialization.Serializable

@Serializable
data class Users(
    val total: Int,
    val users: List<User>
)