package com.example.loginui.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.loginui.MainActivity
import com.example.loginui.R
import com.example.loginui.navigation.repo
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FDKService : FirebaseMessagingService() {
    private lateinit var notificationManager: NotificationManager
    private val context = baseContext
    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        Log.d("11111111", "onCreate: ")
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("aaaaaaaaaaaa", "onMessageReceived: ${message.data.values}")

        message.notification?.let {
            val title = it.title?.split(".")
            val mes = it.body!!
            Log.d("1111", "onMessageReceived: $title")
            Log.d("1111", "onMessageReceived: $mes")
            when(title?.get(0)?.toInt()){
                1-> {
                    sendNotification(mes, title[1], 0)
                }
                2->sendNotification(mes,title[1],1)
//                4->{
//
//                }
            }
        }
    }
    private fun sendNotification(message: String,title: String, channelId :Int) {

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notificationChannel = NotificationChannel(
            "channel$channelId", "Model", NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(notificationChannel)
        val notification = NotificationCompat.Builder(
            context, "channel$channelId"
        ).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .addAction(R.drawable.training,"Start Training",pendingIntent)
            .setContentText(message).setAutoCancel(true)
        notificationManager.notify(channelId,notification.build())
    }

}