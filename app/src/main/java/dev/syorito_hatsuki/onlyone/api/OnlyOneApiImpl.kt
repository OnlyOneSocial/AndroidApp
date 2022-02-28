package dev.syorito_hatsuki.onlyone.api

import dev.syorito_hatsuki.onlyone.api.user.get_users.response.GetUser
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.User
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.Users
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.*
import kotlinx.serialization.json.decodeFromJsonElement
import kotlin.math.log

@Serializable
data class Auth (
    val login: String,
    val password: String
)

class OnlyOneApiImpl(private val httpClient: HttpClient) : OnlyOneApi {
    private lateinit var _token: String
    override  suspend fun getUser(userID:Int): GetUser = httpClient.get {
        url {
            encodedPath = "user/get/${userID}"
        }
        println(_token)
        header("Authorization","Bearer $_token")
    }
    override suspend fun getUsers(): Users = httpClient.get {
        url {
            encodedPath = "user/get"
        }
        println(_token)
        header("Authorization","Bearer $_token")
    }
    override suspend fun getThisUser(): User = httpClient.get {
        url {
            encodedPath = "auth/user"
        }
        header("Authorization","Bearer $_token")
    }

    override fun setToken(token: String): Boolean {
        _token = token
        return true
    }
    override suspend fun auth(login: String,password:String): User = httpClient.post {
        url {
            encodedPath = "auth/login"
        }
        contentType(ContentType.Application.Json)
        body = Auth(login,password)
    }


}