package dev.syorito_hatsuki.onlyone.api

import dev.syorito_hatsuki.onlyone.api.user.get_users.response.GetUser
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.User
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.Users
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.*
import kotlinx.serialization.json.decodeFromJsonElement

class OnlyOneApiImpl(private val httpClient: HttpClient) : OnlyOneApi {
    override suspend fun getUsers(): Users {
        return httpClient.get {
            url {
                encodedPath = "user/get"
            }
        }
    }
    override suspend fun getUser(userID:Int): GetUser {

        val data:JsonObject = httpClient.get {
            url {
                encodedPath = "user/get/${userID}"
            }
        }
        println(data)
        return Json.decodeFromJsonElement(data)

        //return User("dd","",111,"","","",11,false,10,"","","","")
    }
}