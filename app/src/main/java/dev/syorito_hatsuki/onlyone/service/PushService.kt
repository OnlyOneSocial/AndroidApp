package dev.syorito_hatsuki.onlyone.service

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class PushService : FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)


    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val intent = Intent(INTENT_FILTER)
        remoteMessage.data.forEach{ entity ->
            intent.putExtra(entity.key,entity.value)
        }

        sendBroadcast(intent)


    }

    companion object{
        const val INTENT_FILTER = "PUSH_EVENT"
        const val KEY_ACTION = "action"
        const val KEY_MESSAGE = "message"
        const val ACTION_SHOW_MESSAGE = "show_message"
    }
}