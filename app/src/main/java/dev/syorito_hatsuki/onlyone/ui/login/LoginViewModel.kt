package dev.syorito_hatsuki.onlyone.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.syorito_hatsuki.onlyone.R
import dev.syorito_hatsuki.onlyone.api.OnlyOneApi
import dev.syorito_hatsuki.onlyone.data.LoginRepository
import dev.syorito_hatsuki.onlyone.data.Result
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent

val onlyOneApi by KoinJavaComponent.inject<OnlyOneApi>(OnlyOneApi::class.java)


class LoginViewModel() : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    fun testToken(token: String) = flow {
        val data = onlyOneApi.getUserByToken(token)
        emit(data)
    }

    fun LoginUser(username: String, password: String, captcha: String) = flow {
        val data = onlyOneApi.auth(username, password, captcha)
        emit(data)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}