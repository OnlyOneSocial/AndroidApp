package com.onlyone.androidAPP.ui

import androidx.lifecycle.ViewModel
import com.onlyone.androidAPP.api.OnlyOneApi
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class LoginViewModel : ViewModel(), KoinComponent {
    private val onlyOneApi by inject<OnlyOneApi>()
    fun testToken(token: String) = flow {
        val data = onlyOneApi.getUserByToken(token)
        emit(data)
    }

    fun loginUser(username: String, password: String, captcha: String) = flow {
        val data = onlyOneApi.auth(username, password, captcha)
        emit(data)
    }

    fun sendPost(message: String,answer: String) = flow {
        val data = onlyOneApi.sendPost(message, answer)
        emit(data)
    }

    fun setToken(token: String) = flow {
        val data = onlyOneApi.setToken(token)
        emit(data)
    }

    fun getFeed() = flow {
        val data = onlyOneApi.getFeed()
        emit(data)
    }

    fun getUser(id: Int) = flow {
        val data = onlyOneApi.getUser(id)
        emit(data)
    }

    fun getDialogs() = flow {
        val data = onlyOneApi.getDialogs()
        emit(data)
    }

    fun getMessages(userID: Int) = flow {
        val data = onlyOneApi.getMessages(userID)
        emit(data)
    }

    fun getPosts(userID: Int) = flow {
        val data = onlyOneApi.getPosts(userID)
        emit(data)
    }
}