package com.onlyone.androidAPP.api.user.get_users.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val avatar: String,
    val bio: String,
    @SerialName("birthday_date")
    val birthdayDate: Long,
    val city: String,
    val country: String,
    val gender: String,
    val id: Int,
    val me: Boolean,
    val online: Long,
    val sendto: String,
    val status: String,
    val text: String,
    val username: String
)