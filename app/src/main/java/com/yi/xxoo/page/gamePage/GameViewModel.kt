package com.yi.xxoo.page.gamePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.game.Game
import com.yi.xxoo.Room.game.GameDao
import com.yi.xxoo.Room.rank.worldBest.WorldBestRecord
import com.yi.xxoo.Room.rank.worldBest.WorldBestRecordDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameDao: GameDao,
    private val worldBestRecordDao: WorldBestRecordDao
) : ViewModel() {

    private var _gameList = listOf<Game>()
    val gameList: List<Game> get() = _gameList
    val personalBest = UserData.bestRecord

    init {
        // 在viewModelScope中启动协程加载数据
        viewModelScope.launch {
            _gameList = getAllGames()
        }
    }

    private suspend fun getAllGames(): List<Game> {
        return gameDao.getAllGames()
    }
    
    fun getWorldBestRecord(): Flow<List<WorldBestRecord>> {
        return worldBestRecordDao.getWorldBestRecordByGame()
    }
}