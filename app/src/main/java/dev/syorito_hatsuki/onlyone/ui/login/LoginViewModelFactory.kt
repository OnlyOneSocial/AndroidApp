package dev.syorito_hatsuki.onlyone.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.syorito_hatsuki.onlyone.data.LoginDataSource
import dev.syorito_hatsuki.onlyone.data.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}