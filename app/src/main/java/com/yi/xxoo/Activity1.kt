package com.yi.xxoo

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_1)
        val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.d("TAG", "onServiceConnected: 123456798")
                val binder = service as MyService.MyBinder
                val data = binder.getService().data
                binder.getService().start()
                Log.d("TAG", "onServiceConnected: ${binder.getService().message}")
                binder.getService().message = "Activity1"
                Log.d("TAG", "onServiceConnected: ${binder.getService().message}")
                Toast.makeText(this@Activity1, "$data", Toast.LENGTH_SHORT).show()
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Log.d("TAG", "onServiceDisconnected: ")
            }
        }
        bindService(Intent(this, MyService::class.java), connection, BIND_AUTO_CREATE)
    }
}