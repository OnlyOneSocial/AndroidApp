package dev.syorito_hatsuki.onlyone.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.syorito_hatsuki.onlyone.api.OnlyOneApi
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.inject

val onlyOneApi by inject<OnlyOneApi>(OnlyOneApi::class.java)

class MainViewModel : ViewModel() {
    private val _title = MutableLiveData<String>()

    val changeTitle: LiveData<String>
        get() = _title

    fun getUsersList() = flow {
        val users = onlyOneApi.getUsers()
        emit(users)
    }

    fun getUserInfo(userid: Int) = flow {
        val user = onlyOneApi.getUser(userid)
        emit(user)
    }

    fun updateActionBarTitle(title: String) = _title.postValue(title)
}