package com.example.loginui.core

import android.app.Application
import com.google.firebase.FirebaseApp
class ZKDetectionApp : Application(){
    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
    }
}