package com.yi.xxoo.di

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.UserData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import javax.inject.Singleton

object SocketModule {
    var socket = Socket()
    var out: PrintWriter? = null
    var `in`: BufferedReader? = null

    fun connect() {
        Log.d("TAG", "connect: ddddddddddddddddddddd")
        try {
            //10.70.143.129
            //106.52.31.86
            socket = Socket("106.52.31.86", 6666).also { sock ->
                out = PrintWriter(sock.getOutputStream(), true)
                `in` = BufferedReader(InputStreamReader(sock.getInputStream()))
                out!!.println(UserData.account)
            }
        }catch (e:Exception){
            Log.d("TAG", "connect: ddddddddddddd ${e.message}")
        }

    }

    fun disConnectSocket() {
        try {
            socket.close()
            out?.close()
            `in`?.close()
        } catch (e: Exception) {
            println("Error closing resources: ${e.message}")
        }
    }
}

