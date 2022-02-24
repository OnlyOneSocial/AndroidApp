package dev.syorito_hatsuki.onlyone.api

import dev.syorito_hatsuki.onlyone.api.user.get_users.response.Users
import io.ktor.client.*
import io.ktor.client.request.*

class OnlyOneApiImpl(private val httpClient: HttpClient) : OnlyOneApi {
    override suspend fun getUsers(): Users {
        return httpClient.get {
            url {
                encodedPath = "user/get"
            }
        }
    }
}