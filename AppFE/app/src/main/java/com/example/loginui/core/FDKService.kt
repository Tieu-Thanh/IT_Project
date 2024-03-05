package com.example.loginui.core

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FDKService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("111111111111111", "onNewToken: $token")

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("aaaaaaaaaaaa", "onMessageReceived: ${message.data.values}")
        message.notification?.let {
            onMessageReceived(message)
        }
    }
    interface FDKServiceListener{
        fun onMessageReceived(message: RemoteMessage)
    }
}