package com.yi.xxoo.page.gameHistoryPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.history.GameHistory
import com.yi.xxoo.network.gameHistory.GameHistoryService
import com.yi.xxoo.network.onlineGameHistory.OnlineGameHistoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import java.text.SimpleDateFormat
import java.util.Locale

@HiltViewModel
class GameHistoryViewModel @Inject constructor(
    private val gameHistoryService: GameHistoryService,
    private val onlineGameHistoryService: OnlineGameHistoryService
): ViewModel() {

    private val _gameHistory = MutableStateFlow<List<GameHistory>>(emptyList())
    val gameHistory: StateFlow<List<GameHistory>> = _gameHistory

    init {
        fetchGameHistory()
    }

    private fun fetchGameHistory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    Log.d("TAG", "Fetching game history...")

                    val gameHistoryDeferred = async {
                        gameHistoryService.getGameHistory(UserData.account).firstOrNull()
                    }
                    val onlineGameHistoryDeferred = async {
                        onlineGameHistoryService.getOnlineGameHistoryPre(UserData.account).firstOrNull()
                    }

                    val gameHistory = gameHistoryDeferred.await()
                    val onlineGameHistory = onlineGameHistoryDeferred.await()

                    Log.d("TAG", "Fetched game history flows")

                    if (gameHistory != null && onlineGameHistory != null) {
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

                        // 将两个数据源的结果合并为一个列表
                        val combinedList = gameHistory.data + onlineGameHistory.data
                        Log.d("TAG", "Combined list before sorting: $combinedList")

                        // 按startTime排序
                        val sortedList = combinedList.sortedBy { item ->
                            dateFormat.parse(item.startTime) // 将startTime字符串解析为Date对象
                        }

                        // 更新StateFlow的值
                        Log.d("TAG", "Final combined list: $sortedList")
                        _gameHistory.value = sortedList.toList()
                    } else {
                        Log.e("TAG", "One of the game history flows is null")
                    }
                } catch (e: Exception) {
                    Log.e("TAG", "Error occurred while fetching game history", e)
                }
            }
        }
    }
}