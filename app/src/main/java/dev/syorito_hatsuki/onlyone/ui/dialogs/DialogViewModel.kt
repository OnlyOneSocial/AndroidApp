package dev.syorito_hatsuki.onlyone.ui.dialogs

import androidx.lifecycle.ViewModel
import dev.syorito_hatsuki.onlyone.api.OnlyOneApi
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.inject

val onlyOneApi by inject<OnlyOneApi>(OnlyOneApi::class.java)

class DialogViewModel : ViewModel() {
    fun getDialogs() = flow {
        val users = onlyOneApi.getDialogs()
        emit(users)
    }
}