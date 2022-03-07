package dev.syorito_hatsuki.onlyone.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.syorito_hatsuki.onlyone.api.OnlyOneApi
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.User
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent

val onlyOneApi by KoinJavaComponent.inject<OnlyOneApi>(OnlyOneApi::class.java)

class UserPage : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text

    fun getUserInfo(userid: Int) = flow {
        var userID = userid
        if (userid == 0) {
            userID = onlyOneApi.getThisUser().id
        }
        val user = onlyOneApi.getUser(userID)
        emit(user)
    }

    fun getPostUser(userid: Int) = flow {
        var userID = userid
        if (userid == 0) {
            userID = onlyOneApi.getThisUser().id
        }
        val posts = onlyOneApi.getPosts(userID)
        emit(posts)
    }
}