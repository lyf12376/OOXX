package com.yi.xxoo.page.matchPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.user.UserDao
import com.yi.xxoo.bean.rankGame.RankGameRequest
import com.yi.xxoo.network.Match.MatchResponse
import com.yi.xxoo.network.Match.MatchService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(private val matchService: MatchService,private val userDao: UserDao) : ViewModel() {


    suspend fun getUserData() {
        UserData.setUser(userDao.getUserByEmail("account1"))
    }

    //计时器
    private val _time = MutableStateFlow(0L)
    val time = _time.asStateFlow()
    private var timerJob = viewModelScope.launch { }
    //是否开始匹配
    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    fun startTimer() {
        if (_isRunning.value) return
        _isRunning.value = true
        timerJob = viewModelScope.launch {
            while (_isRunning.value) {
                delay(1000)
                _time.value += 1
            }
        }
    }

    fun resetTimer() {
        _isRunning.value = false
        timerJob.cancel()
        _time.value = 0L
    }

    private val _message = MutableStateFlow(MatchResponse(0,"", emptyList()))
    val message = _message.asStateFlow()
    fun matching(){
        //viewModelScope默认主线程
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // 在IO线程执行网络请求
                matchService.matching(RankGameRequest(UserData.account,UserData.name)).collect {
                    _message.value = it
                }
            }
        }
    }

    private val _rejected = MutableStateFlow(false)
    val rejected: StateFlow<Boolean> = _rejected

    private var socket: Socket? = null
    private var out: PrintWriter? = null
    private var `in`: BufferedReader? = null

    fun connectSocket() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    socket = Socket("10.70.143.129", 6666).also { sock ->
                        out = PrintWriter(sock.getOutputStream(), true)
                        `in` = BufferedReader(InputStreamReader(sock.getInputStream()))
                    }
                    sendMessage(UserData.account)
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    out?.println(message)
                    val response = `in`?.readLine()
                    println("Received: $response")
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                    _rejected.value = true
                }
            }
        }
    }

    fun disConnectSocket() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    socket?.close()
                    out?.close()
                    `in`?.close()
                } catch (e: Exception) {
                    println("Error closing resources: ${e.message}")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        try {
            socket?.close()
            out?.close()
            `in`?.close()
        } catch (e: Exception) {
            println("Error closing resources: ${e.message}")
        }
    }

    //匹配成功
    private val _matched = MutableStateFlow(false)
    val matched: StateFlow<Boolean> = _matched

    fun matchSuccess(){
        _matched.value = true
    }

    fun acceptedMatch()
    {
        sendMessage("accepted")
    }




}