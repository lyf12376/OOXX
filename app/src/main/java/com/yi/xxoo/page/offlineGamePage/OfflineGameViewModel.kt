package com.yi.xxoo.page.offlineGamePage

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.game.Game
import com.yi.xxoo.Room.game.GameDao
import com.yi.xxoo.Room.rank.worldBest.WorldBestRecord
import com.yi.xxoo.Room.rank.worldBest.WorldBestRecordDao
import com.yi.xxoo.Room.user.UserDao
import com.yi.xxoo.utils.RoomUtils.personalBestRecordToString
import com.yi.xxoo.utils.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineGameViewModel @Inject constructor(
    private val gameDao: GameDao,
    private val worldBestRecordDao: WorldBestRecordDao,
    private val userDao: UserDao
) : ViewModel() {

    private var gameList : Flow<List<Game>> = getAllGames()
    //val gameList: List<Game> get() = _gameList
    val personalBest = UserData.bestRecord



    //游戏内容
    var worldBest = ""
    var target = ""

    private val _gameSuccess = MutableStateFlow(false)
    val gameSuccess: StateFlow<Boolean> = _gameSuccess


    private fun getAllGames(): Flow<List<Game>> {
        return gameDao.getAllGames()
    }
    
    fun getWorldBestRecord(): Flow<List<WorldBestRecord>> {
        return worldBestRecordDao.getWorldBestRecordByGame()
    }

    private val _gameDetail = MutableStateFlow<Game?>(null)
    val gameDetail: StateFlow<Game?> = _gameDetail

    fun getGame(level: Int) {
        viewModelScope.launch {
            gameList.collect { games ->
                if (games.isNotEmpty() && level-1 < games.size) {
                    _gameDetail.value = games[level - 1]
                } else {
                    _gameDetail.value = null
                }
            }
        }
    }


    fun check(now:String,time:Int,level: Int){
        viewModelScope.launch {
            Log.d("TAG", "check: $now \n $target")
            if(now == target){
                //胜利
                if (UserData.bestRecord[level-1] == 0 || UserData.bestRecord[level-1] > time){
                    UserData.bestRecord[level-1] = time
                    Log.d("TAG", "check: success")
                    userDao.updateBestRecordByEmail(UserData.email,UserData.bestRecord.personalBestRecordToString())
                    _gameSuccess.value = true

                }else{
                    _gameSuccess.value = true
                }
                if (UserData.passNum == level-1){
                    userDao.updateUserPassNum(level,UserData.email)
                    UserData.passNum = level
                    userDao.updateUserCoin(UserData.email,UserData.coin+10)
                    UserData.coin += 10
                }

            }
        }

    }

    fun loadMusic(context: Context,musicId:Int)
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




}

