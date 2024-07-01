package com.yi.xxoo.page.gameHistoryPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.GameMode
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.history.GameHistory
import com.yi.xxoo.Room.history.GameHistoryDao
import com.yi.xxoo.network.gameHistory.GameHistoryService
import com.yi.xxoo.network.onlineGameHistory.OnlineGameHistoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Date
import javax.inject.Inject
import java.text.SimpleDateFormat
import java.util.Locale

@HiltViewModel
class GameHistoryViewModel @Inject constructor(
    private val gameHistoryService: GameHistoryService,
    private val onlineGameHistoryService: OnlineGameHistoryService,
    private val gameHistoryDao: GameHistoryDao
): ViewModel() {

    private val _gameHistory = MutableStateFlow<List<GameHistory>>(emptyList())
    val gameHistory: StateFlow<List<GameHistory>> = _gameHistory

    private val _onlineGameHistory = MutableStateFlow<List<GameHistory>>(emptyList())
    val onlineGameHistory:StateFlow<List<GameHistory>> = _onlineGameHistory

    private val _historyList = MutableStateFlow<List<GameHistory>>(emptyList())
    val historyList:StateFlow<List<GameHistory>> = _historyList

    init {
        setupGameHistoryObservation()
    }

    private fun setupGameHistoryObservation() {
        viewModelScope.launch {
            // 初始化时获取历史记录
            fetchGameHistory()
            fetchOnlineGameHistory()

            // 使用combine来观察两个Flow的更新，并在有新值时执行合并排序
            combine(_gameHistory, _onlineGameHistory) { gameHistory, onlineHistory ->
                mergeAndSortGameHistories(gameHistory, onlineHistory)
            }.launchIn(viewModelScope)
        }
    }

    private fun fetchGameHistory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    if (GameMode.isNetworkEnabled){
                        withContext(Dispatchers.IO) {
                            val gameHistory = gameHistoryService.getGameHistory(UserData.account).firstOrNull()?.data
                            if (gameHistory!=null){
                                _gameHistory.value = gameHistory
                            }
                        }
                    }else{
                        try {
                            gameHistoryDao.getGameHistory(UserData.account).collect{
                                _gameHistory.value = it.toList()
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

        }
    }

    private fun fetchOnlineGameHistory() {
        viewModelScope.launch {
            if (GameMode.isNetworkEnabled) {
                withContext(Dispatchers.IO) {
                    try {
                        val onlineHistory = onlineGameHistoryService.getOnlineGameHistoryPre(UserData.account).firstOrNull()
                        if (onlineHistory != null) {
                            _onlineGameHistory.value = onlineHistory.data
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private suspend fun mergeAndSortGameHistories(localGameHistory: List<GameHistory>, onlineGameHistory: List<GameHistory>) {
        withContext(Dispatchers.IO) {
            val combinedHistory = localGameHistory + onlineGameHistory
            val dateFormatter = SimpleDateFormat("yyyy-M-d H:m:s")
            val sortedHistory = combinedHistory.sortedWith(compareByDescending {
                try {
                    dateFormatter.parse(it.startTime)
                } catch (e: Exception) {
                    "1970-1-1 0:0:0"
                }
            })
            _historyList.value = sortedHistory.toList()
        }
    }
}