package com.yi.xxoo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    val TAG = "MyService"
    val data = 1234567890
    var message = "Service"

    override fun onBind(intent: Intent): IBinder {
        return MyBinder()
    }

    fun start() {
        Log.d(TAG, "start: Service is started")
    }

    class MyBinder : android.os.Binder() {
        fun getService(): MyService {
            return MyService()
        }
    }
}