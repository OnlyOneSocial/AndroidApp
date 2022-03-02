package dev.syorito_hatsuki.onlyone.api

import android.service.autofill.UserData
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.*

interface OnlyOneApi {
    suspend fun getUsers(): Users
    suspend fun getDialogs(): List<Dialog>
    suspend fun getMessages(userID: Int): List<DialogMessage>
    suspend fun getThisUser(token: String): User
    suspend fun getUser(userID: Int): GetUser
    fun setToken(token: String): Boolean
    suspend fun auth(login: String,password:String,captcha: String): UserAuth
}