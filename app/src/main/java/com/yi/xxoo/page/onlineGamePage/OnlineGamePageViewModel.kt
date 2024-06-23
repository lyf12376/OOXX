package com.yi.xxoo.page.onlineGamePage

import RankGame
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.OnlineGame
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.game.Game
import com.yi.xxoo.bean.rankGame.PlayerSettlement
import com.yi.xxoo.bean.rankGame.SubmissionRecord
import com.yi.xxoo.di.SocketModule
import com.yi.xxoo.network.match.MatchService
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
class OnlineGamePageViewModel @Inject constructor(private val matchService: MatchService):ViewModel(){

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

    private val _chatMessage = MutableStateFlow("")
    val chatMessage:StateFlow<String> = _chatMessage

    private val submitRecord:MutableList<SubmissionRecord> = emptyList<SubmissionRecord>().toMutableList()

    fun check(now:String,time: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if(now == OnlineGame.target){
                    _gameSuccess.value = true
                    _showSuccessAnim.value = true
                    submitRecord.add(SubmissionRecord(time,1))
                    matchService.submit(PlayerSettlement(OnlineGame.gameId,RankGame(UserData.account,UserData.name),1,time,submitRecord))
                }
                else{
                    submitRecord.add(SubmissionRecord(time,0))
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

    fun chat(message:String){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    SocketModule.out?.println("chat/${OnlineGame.gameId}/${UserData.account}/$message")
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
                    }
                } catch (e: Exception) {
                    println("Error listening for messages: ${e.message}")
                }
            }
        }
    }

    private fun handleMessage(message: String) {
        if (message.contains("submit")){
            _isEnemySubmit.value = true
        }
        if (message.contains("chat")){

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
        exitGame()
    }
}