package com.yi.xxoo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.yi.xxoo.Room.LoginTime
import com.yi.xxoo.Room.TimeDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_login2)
        val context = this
        val TAG = "LoginActivity"

        Log.d(TAG, "onCreate: 111111")
        findViewById<Button>(R.id.button).setOnClickListener {
            try {
                Log.d(TAG, "onCreate: 22212222")
                val sp = getSharedPreferences("login", MODE_PRIVATE)
                val account = findViewById<EditText>(R.id.Account).text.toString()
                val password = findViewById<EditText>(R.id.password).text.toString()
                Log.d(TAG, "onCreate: 3333333")
                Log.d(TAG, "onCreate: $account")
                Log.d(TAG, "onCreate: $password")

                sp.edit().putString("account", account).putString("password", password).apply()
                Log.d(TAG, "onCreate: 4444444")
                val time = System.currentTimeMillis()
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val timeStr = format.format(time)

                Log.d(TAG, "onCreate: $timeStr")

                CoroutineScope(Dispatchers.IO).launch {
                    val timeDao = TimeDataBase.getDatabase(context.applicationContext).timeDao()
                    val a = timeDao.saveTime(LoginTime(time = timeStr))
                    Log.d(TAG, "onCreate: $a")
                }
                Log.d(TAG, "onCreate: 6666666")
            } catch (e: Exception) {
                Log.d("Tag", "onCreate================: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}