package dev.syorito_hatsuki.onlyone.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flow


class HomeViewModel : ViewModel() {

        fun getUsersList() = flow {
                val users = onlyOneApi.getUsers()
                emit(users)
        }

        private val _text = MutableLiveData<String>().apply {
                value = "This is home Fragment"
        }
        val text: LiveData<String> = _text
}
