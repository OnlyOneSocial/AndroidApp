package com.onlyone.androidAPP.api

import com.onlyone.androidAPP.api.user.get_users.response.*

interface OnlyOneApi {
    suspend fun getUsers(): Users
    suspend fun getDialogs(): List<Dialog>
    suspend fun getMessages(userID: Int): List<DialogMessage>
    suspend fun getUserByToken(token: String): User
    suspend fun getThisUser(): User
    suspend fun setFBMToken(token: String): Boolean
    suspend fun getUser(userID: Int): GetUser
    suspend fun getPosts(userID: Int): List<PostsDataItem>
    suspend fun getFeed(): List<PostsDataItem>
    fun setToken(token: String): Boolean
    suspend fun sendPost(message: String,answer: String): PostsDataItem
    suspend fun auth(login: String, password: String, captcha: String): UserAuth
}