package com.onlyone.androidAPP.service
/*
import android.content.Intent
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.onlyone.androidAPP.api.OnlyOneApi
import org.koin.java.KoinJavaComponent

val onlyOneApi by KoinJavaComponent.inject<OnlyOneApi>(OnlyOneApi::class.java)

class PushService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val intent = Intent(INTENT_FILTER)
        remoteMessage.data.forEach { entity ->
            intent.putExtra(entity.key, entity.value)
        }

        sendBroadcast(intent)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("Refreshed token:",token);
    }

    companion object {
        const val INTENT_FILTER = "PUSH_EVENT"
        const val KEY_ACTION = "action"
        const val KEY_MESSAGE = "message"
        const val ACTION_SHOW_MESSAGE = "show_message"
    }
}*/