package dev.syorito_hatsuki.onlyone.ui.users

import androidx.lifecycle.ViewModel
import dev.syorito_hatsuki.onlyone.api.OnlyOneApi
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.inject

val onlyOneApi by inject<OnlyOneApi>(OnlyOneApi::class.java)

class MainViewModel : ViewModel() {
    fun getUsersList() = flow {
        val users = onlyOneApi.getUsers()
        emit(users)
    }

    fun getFeed() = flow {
        val posts = dev.syorito_hatsuki.onlyone.ui.fragments.onlyOneApi.getFeed()
        emit(posts)
    }
}