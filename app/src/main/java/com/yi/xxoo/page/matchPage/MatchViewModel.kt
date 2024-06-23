package com.yi.xxoo.page.matchPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.OnlineGame
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.user.User
import com.yi.xxoo.Room.user.UserDao
import com.yi.xxoo.bean.rankGame.RankGameRequest
import com.yi.xxoo.bean.rankGame.RankGameResult
import com.yi.xxoo.di.AppModule
import com.yi.xxoo.di.SocketModule
import com.yi.xxoo.di.SocketModule.`in`
import com.yi.xxoo.di.SocketModule.out
import com.yi.xxoo.network.match.MatchResponse
import com.yi.xxoo.network.match.MatchService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.PrintWriter
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val matchService: MatchService,
    private val userDao: UserDao,
) : ViewModel() {
    suspend fun creatUser(){
        userDao.createUser(User("name","account","password","account",photo = "",bestRecord = "123,45"))
    }

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

    private val _message = MutableStateFlow(MatchResponse(0, "", emptyList()))
    val message = _message.asStateFlow()
    fun matching() {
        //viewModelScope默认主线程
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // 在IO线程执行网络请求
                matchService.matching(RankGameRequest(UserData.account, UserData.name,0)).collect {
                    _message.value = it
                }
            }
        }
    }

    fun cancel() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // 在IO线程执行网络请求
                matchService.cancel(RankGameRequest(UserData.account, UserData.name,0))
            }
        }
    }

    //有人拒绝了开始游戏
    private val _rejected = MutableStateFlow(false)
    val rejected: StateFlow<Boolean> = _rejected

    //双方都准备，准备开始游戏
    private val _startGame = MutableStateFlow(false)
    val startGame: StateFlow<Boolean> = _startGame

    //用户接受
    private val _accept = MutableStateFlow(false)
    val accept :StateFlow<Boolean> = _accept

    fun connectSocket() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    SocketModule.connect()
                    sendMessage(UserData.account)
                    listenForMessages()
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }
            }
        }
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    out?.println(message)
                } catch (e: Exception) {
                    Log.d("TAGdasdsadasda", "sendMessage: ${e.message}")
                }
            }
        }
    }

    private fun listenForMessages() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    while (true) {
                        val message = `in`?.readLine()
                        if (message != null) {
                            handleMessage(message)
                            break
                        }
                    }
                } catch (e: Exception) {
                    println("Error listening for messages: ${e.message}")
                }
            }
        }
    }

    private fun handleMessage(message: String) {
        if (message.contains("ok")) {
            OnlineGame.setOnlineGame(
                message.split("/")[1],
                message.split("/")[2],
                message.split("/")[3]
            )
            _startGame.value = true
        }
        if (message == "rejected") {
            _rejected.value = true
        }
    }



    //匹配成功
    private val _matched = MutableStateFlow(false)
    val matched: StateFlow<Boolean> = _matched

    fun matchSuccess(data: List<RankGameResult>) {
        _matched.value = true
        _accept.value = true
        for (i in data) {
            if (i.userAccount != UserData.account) {
                OnlineGame.enemyName = i.userName
                OnlineGame.enemyRank = i.rank
            }
        }
    }

    fun acceptedMatch() {
        _accept.value = false
        sendMessage("accepted/${UserData.account}")
    }

    fun rejectMatch() {
        sendMessage("rejected/${UserData.account}")
    }


}