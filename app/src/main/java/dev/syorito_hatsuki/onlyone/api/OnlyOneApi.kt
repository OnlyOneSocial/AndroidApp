package dev.syorito_hatsuki.onlyone.api

import dev.syorito_hatsuki.onlyone.api.user.get_users.response.*

interface OnlyOneApi {
    suspend fun getUsers(): Users
    suspend fun getDialogs(): List<Dialog>
    suspend fun getMessages(userID: Int): List<DialogMessage>
    suspend fun getUserByToken(token: String): User
    suspend fun getThisUser(): User
    suspend fun setFBMToken(token: String) : Boolean
    suspend fun getUser(userID: Int): GetUser
    suspend fun getPosts(userID: Int): List<PostsDataItem>
    suspend fun getFeed(): List<PostsDataItem>
    fun setToken(token: String): Boolean
    suspend fun auth(login: String, password: String, captcha: String): UserAuth
}