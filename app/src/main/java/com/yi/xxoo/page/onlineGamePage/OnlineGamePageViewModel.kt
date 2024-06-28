package com.yi.xxoo.page.onlineGamePage

import RankGame
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.OnlineGame
import com.yi.xxoo.Const.Settlement
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.game.Game
import com.yi.xxoo.bean.rankGame.Message
import com.yi.xxoo.bean.rankGame.PlayerSettlement
import com.yi.xxoo.bean.rankGame.SubmissionRecord
import com.yi.xxoo.di.SocketModule
import com.yi.xxoo.network.gameTime.GameTimeService
import com.yi.xxoo.network.match.MatchService
import com.yi.xxoo.network.user.UserService
import com.yi.xxoo.utils.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OnlineGamePageViewModel @Inject constructor(
    private val matchService: MatchService,
    private val userService: UserService,
    private val gameTimeService: GameTimeService
):ViewModel(){

    private val _gameSuccess = MutableStateFlow(false)
    val gameSuccess: StateFlow<Boolean> = _gameSuccess

    private val _showSuccessAnim = MutableStateFlow(false)
    val showSuccessAnim:StateFlow<Boolean> = _showSuccessAnim

    private val _goSettlement = MutableStateFlow(false)
    val goSettlement:StateFlow<Boolean> = _goSettlement

    private val _isEnemySubmit = MutableStateFlow(false)
    val isEnemySubmit:StateFlow<Boolean> = _isEnemySubmit

    private val _isChatEnabled = MutableStateFlow(false)
    val isChatEnabled:StateFlow<Boolean> = _isChatEnabled

    private val _settled = MutableStateFlow(false)
    val settled:StateFlow<Boolean> = _settled

    private val _isGridEnabled = MutableStateFlow(true)
    val isGridEnabled:StateFlow<Boolean> = _isGridEnabled

    private val _waitSettled = MutableStateFlow(false)
    val waitSettled:StateFlow<Boolean> = _waitSettled

    private val _goSettle = MutableStateFlow(false)
    val goSettle:StateFlow<Boolean> = _goSettle

    private val _submitFailed = MutableStateFlow(false)
    val submitFailed: StateFlow<Boolean> = _submitFailed

    private val _showSettleDialog = MutableStateFlow(false)
    val showSettleDialog:StateFlow<Boolean> = _showSettleDialog

    fun showSettleDialog()
    {
        _showSettleDialog.value = true
    }

    fun closeSettleDialog()
    {
        _showSettleDialog.value = false
    }

    fun reSubmit() {
        _submitFailed.value = false
    }

    fun waitSettle()
    {
        _waitSettled.value = true
    }




    private val submitRecord:MutableList<SubmissionRecord> = emptyList<SubmissionRecord>().toMutableList()

    init {
        listenForMessages()
    }
    fun check(now:String,time: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    if (now == OnlineGame.target) {
                        _gameSuccess.value = true
                        _showSuccessAnim.value = true
                        submitRecord.add(SubmissionRecord(time, 1))
                        matchService.submit(
                            PlayerSettlement(
                                OnlineGame.gameId,
                                RankGame(UserData.account, UserData.name),
                                1,
                                time,
                                submitRecord
                            )
                        )
                        UserData.time += time
                        UserData.gameTimes++
                        userService.updateTime(UserData.account, UserData.time)
                        userService.updateGameTimes(UserData.account, UserData.gameTimes)
                        Settlement.setUserSettlement(time, submitRecord.size)
                        _settled.value = true
                        _isGridEnabled.value = false
                    } else {
                        submitRecord.add(SubmissionRecord(time, 0))
                        _submitFailed.value = true
                    }
                }catch (e:Exception){
                    Log.d("TAG", "check: ${e.message}")
                }
            }
        }
    }

    fun cancelAnim()
    {
        _showSuccessAnim.value = false
    }

    fun exitGame()
    {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                matchService.submit(PlayerSettlement(OnlineGame.gameId,RankGame(UserData.account,UserData.name),0,0,submitRecord))
            }
        }
    }


    private fun sendMessage(message: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    SocketModule.out?.println(message)
                } catch (e: Exception) {
                    Log.d("TAGdasdsadasda", "sendMessage: ${e.message}")
                }
            }
        }
    }

    private val _chatMessages = MutableStateFlow(emptyList<Message>())
    val chatMessages:StateFlow<List<Message>> = _chatMessages

    fun chat(message:Message){
        viewModelScope.launch {
            _chatMessages.value += message
            Log.d("TAG", "chat: ${_chatMessages.value}")
            withContext(Dispatchers.IO) {
                try {
                    SocketModule.out?.println("chat/${OnlineGame.gameId}/${UserData.account}/${message.text}")
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
                        val message = SocketModule.`in`?.readLine()
                        if (message != null) {
                            handleMessage(message)
                        }
                        //循环的退出逻辑，在用户进入结算页面或者退出游戏结束
                        if (_goSettle.value){
                            SocketModule.disConnectSocket()
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
        if (message.contains("submit")){
            Log.d("TAG", "handleMessage: $message")
            val split = message.split("/")
            Settlement.enemyPassTime = split[1].toInt()
            Settlement.enemySubmitTimes = split[2].toInt()
            _isEnemySubmit.value = true
            if (_waitSettled.value){
                _goSettle.value = true
            }
        }
        if (message.contains("chat")){
            val split = message.split("/")
            val text = split[1]
            _chatMessages.value += Message(text,false)
        }
    }


    fun loadMusic(context: Context, musicId:Int)
    {
        SoundManager.loadSound(context,musicId)
    }

    fun playMusic()
    {
        SoundManager.playSound()
    }

    fun releaseMusic()
    {
        SoundManager.release()
    }

    override fun onCleared() {
        super.onCleared()
        if (_settled.value.not()){
            exitGame()
        }
        SocketModule.disConnectSocket()
    }
}