package com.yi.xxoo.page.rankPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Room.rank.passNum.PassNumRank
import com.yi.xxoo.Room.rank.time.GameTimeRank
import com.yi.xxoo.network.gameTime.GameTimeService
import com.yi.xxoo.network.passNumRank.PassNumRankService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RankViewModel @Inject constructor(
    private val passNumRankService: PassNumRankService,
    private val gameTimeService: GameTimeService
) : ViewModel() {

    private val _passNumRankListFlow = MutableStateFlow<List<PassNumRank>>(emptyList())
    val passNumRankList: StateFlow<List<PassNumRank>> get() = _passNumRankListFlow

    private val _gameTimeRankListFlow = MutableStateFlow<List<GameTimeRank>>(emptyList())
    val gameTimeRankList: StateFlow<List<GameTimeRank>> get() = _gameTimeRankListFlow

    init {
        fetchPassNumRankList()
        fetchGameTimeRankList()
    }

    private fun fetchPassNumRankList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    passNumRankService.getPassNumRank().collect { passNumRanks ->
                        // 创建一个新的列表并赋值给StateFlow
                        _passNumRankListFlow.value = passNumRanks.data.toList()
                        Log.d("TAG", "fetchPassNumRankList: ${_passNumRankListFlow.value}")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun fetchGameTimeRankList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    gameTimeService.getGameTimeRank().collect { gameTimeRanks ->
                        // 创建一个新的列表并赋值给StateFlow
                        _gameTimeRankListFlow.value = gameTimeRanks.data.toList()
                        Log.d("TAG", "fetchPassNumRankList: ${_gameTimeRankListFlow.value}")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}