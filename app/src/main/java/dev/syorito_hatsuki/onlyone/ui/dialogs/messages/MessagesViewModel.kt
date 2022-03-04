package dev.syorito_hatsuki.onlyone.ui.dialogs.messages

import androidx.lifecycle.ViewModel
import dev.syorito_hatsuki.onlyone.api.OnlyOneApi
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.inject

val onlyOneApi by inject<OnlyOneApi>(OnlyOneApi::class.java)

class DialogViewModel : ViewModel() {
    fun getMessages(userid: Int) = flow {
        val users = onlyOneApi.getMessages(userID = userid)
        emit(users)
    }
}