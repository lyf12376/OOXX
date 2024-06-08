package com.yi.xxoo.page.gamePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.User
import com.yi.xxoo.Room.game.Game
import com.yi.xxoo.Room.game.GameDao
import com.yi.xxoo.Room.rank.worldBest.WorldBestRecord
import com.yi.xxoo.Room.rank.worldBest.WorldBestRecordDao
import com.yi.xxoo.Room.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sqrt

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameDao: GameDao,
    private val worldBestRecordDao: WorldBestRecordDao
) : ViewModel() {

    private var _gameList = listOf<Game>()
    val gameList: List<Game> get() = _gameList
    val personalBest = User.bestRecord

    init {
        // 在viewModelScope中启动协程加载数据
        viewModelScope.launch {
            _gameList = getAllGames()
        }
    }

    // 假设 getAllGames() 是挂起函数
    private suspend fun getAllGames(): List<Game> {
        return gameDao.getAllGames()
    }

    // 不变的getWorldBestRecord()方法
    fun getWorldBestRecord(): Flow<List<WorldBestRecord>> {
        return worldBestRecordDao.getWorldBestRecordByGame()
    }
}