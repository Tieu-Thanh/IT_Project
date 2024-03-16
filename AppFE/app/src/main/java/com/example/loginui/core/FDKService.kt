package com.example.loginui.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.loginui.DisplayVideoActivity
import com.example.loginui.MainActivity
import com.example.loginui.R
import com.example.loginui.navigation.repo
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class FDKService : FirebaseMessagingService() {
    private lateinit var notificationManager: NotificationManager
    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.let {
            val title = it.title?.split(".")
            val mes = it.body!!
            when(title?.get(0)?.toInt()){
                1-> sendNotification(mes, title[1], 0,"Start Training",mainActivityIntent())
                2-> sendNotification(mes, title[1],1,"Test",mainActivityIntent())
                4-> {
                    sendNotification(mes, title[1],2,"Check",displayVideoURL(message.data["url"]!!,message.data["count_result"]!!.toInt()))
                }
            }
        }
    }
    private fun displayVideoURL(url:String,count_result:Int):PendingIntent{
        val intent = Intent(applicationContext, DisplayVideoActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("url",url)
            putExtra("count_result",count_result)
        }
        return PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
    }
    private fun mainActivityIntent(): PendingIntent {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    private fun sendNotification(message: String,title: String, channelId :Int,mesTitle:String, pendingIntent: PendingIntent) {
        val notificationChannel = NotificationChannel(
            "channel$channelId", "Model", NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(notificationChannel)
        val notification = NotificationCompat.Builder(
            applicationContext, "channel$channelId"
        ).setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .addAction(R.drawable.training,mesTitle,pendingIntent)
            .setContentText(message).setAutoCancel(true)
        notificationManager.notify(channelId,notification.build())
    }

}