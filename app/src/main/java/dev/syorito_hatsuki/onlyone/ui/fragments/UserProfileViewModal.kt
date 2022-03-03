package dev.syorito_hatsuki.onlyone.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.syorito_hatsuki.onlyone.api.OnlyOneApi
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent

val onlyOneApi by KoinJavaComponent.inject<OnlyOneApi>(OnlyOneApi::class.java)

class UserPage : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text

    fun getUserInfo(userid: Int) = flow {
        val user = onlyOneApi.getUser(userid)
        emit(user)
    }
}