package com.onlyone.androidAPP.api


import com.onlyone.androidAPP.api.user.get_users.response.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class Auth(
    val username: String,
    val password: String,
    val captcha: String
)

@Serializable
data class MessageSend(
    val text: String,
    val answer: String,
)

class OnlyOneApiImpl(private val httpClient: HttpClient) : OnlyOneApi {
    private lateinit var _token: String

    override suspend fun getPosts(userID: Int): List<PostsDataItem> = httpClient.get {
        url {
            encodedPath = "wall/get/${userID}"
        }
        header("Authorization", "Bearer $_token")
    }

    override suspend fun getFeed(): List<PostsDataItem> = httpClient.get {
        url {
            encodedPath = "wall/get?offset=0&limit=20"
        }
        header("Authorization", "Bearer $_token")
    }

    override suspend fun getUser(userID: Int): GetUser = httpClient.get {
        url {
            encodedPath = "user/get/${userID}"
        }
        header("Authorization", "Bearer $_token")
    }

    override suspend fun getDialogs(): List<Dialog> = httpClient.get {
        url {
            encodedPath = "message/im"
        }
        header("Authorization", "Bearer $_token")
    }

    override suspend fun setFBMToken(token: String): Boolean = httpClient.post {
        url {
            encodedPath = "user/setFBMToken"
        }
        header("Authorization", "Bearer $_token")
    }

    override suspend fun getMessages(userID: Int): List<DialogMessage> = httpClient.get {
        url {
            encodedPath = "message/get/$userID"
        }
        header("Authorization", "Bearer $_token")
    }

    override suspend fun getUsers(): Users = httpClient.get {
        url {
            encodedPath = "user/get"
        }
        header("Authorization", "Bearer $_token")
    }

    override suspend fun getUserByToken(token: String): User = httpClient.get {
        url {
            encodedPath = "user/thisuser"
        }
        header("Authorization", "Bearer $token")
    }

    override suspend fun getThisUser(): User = httpClient.get {
        url {
            encodedPath = "user/thisuser"
        }
        header("Authorization", "Bearer $_token")
    }

    override fun setToken(token: String): Boolean {
        _token = token
        println("status")
        return true
    }

    override suspend fun auth(login: String, password: String, captcha: String): UserAuth =
        httpClient.post {
            url {
                encodedPath = "user/login_android"
            }
            contentType(ContentType.Application.Json)
            body = Auth(login, password, captcha)
        }

    override suspend fun sendPost(message: String,answer: String): PostsDataItem =
        httpClient.post {
            url {
                encodedPath = "wall/send"
            }
            header("Authorization", "Bearer $_token")
            contentType(ContentType.Application.Json)
            body = MessageSend(message, answer)
        }
}