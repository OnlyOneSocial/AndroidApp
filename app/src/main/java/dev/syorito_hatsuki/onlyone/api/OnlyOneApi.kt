package dev.syorito_hatsuki.onlyone.api

import android.service.autofill.UserData
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.GetUser
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.Users

interface OnlyOneApi {
    suspend fun getUsers(): Users
    suspend fun getUser(userID: Int): GetUser
}