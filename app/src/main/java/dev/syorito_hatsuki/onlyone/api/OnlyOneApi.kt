package dev.syorito_hatsuki.onlyone.api

import dev.syorito_hatsuki.onlyone.api.user.get_users.response.Users

interface OnlyOneApi {
    suspend fun getUsers(): Users
}