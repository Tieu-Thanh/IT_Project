package com.example.loginui.core

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        // For communication return IBinder implementation
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // Perform your long-running operations here.

        // Using START_NOT_STICKY to prevent the service from being recreated after being killed by the system
        return START_NOT_STICKY
    }
}