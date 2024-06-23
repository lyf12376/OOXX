package com.yi.xxoo.page.onlineGamePage

import RankGame
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.OnlineGame
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.game.Game
import com.yi.xxoo.bean.rankGame.PlayerSettlement
import com.yi.xxoo.bean.rankGame.SubmissionRecord
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

    private val submitRecord:MutableList<SubmissionRecord> = emptyList<SubmissionRecord>().toMutableList()

    fun check(now:String,time: Int){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if(now == OnlineGame.target){
                    _gameSuccess.value = true
                    submitRecord.add(SubmissionRecord(time,1))
                    matchService.submit(PlayerSettlement(OnlineGame.gameId,RankGame(UserData.account,UserData.name),1,time,submitRecord))
                }
                else{
                    submitRecord.add(SubmissionRecord(time,0))
                }
            }
        }
    }

    fun exitGame()
    {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                matchService.submit(PlayerSettlement(OnlineGame.gameId,RankGame(UserData.account,UserData.name),0,0,submitRecord))
            }
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