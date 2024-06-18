package com.yi.xxoo.page.matchPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.bean.rankGame.RankGameRequest
import com.yi.xxoo.network.Match.MatchResponse
import com.yi.xxoo.network.Match.MatchService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(private val matchService: MatchService) : ViewModel() {
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

    private val _message = MutableStateFlow(MatchResponse(0,"", emptyList()))
    val message = _message.asStateFlow()
    fun matching(){
        //viewModelScope默认主线程
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // 在IO线程执行网络请求
                matchService.matching(RankGameRequest(UserData.account,UserData.name)).collect {
                    // 回到主线程执行结果处理
                    _message.value = it
                }
            }
        }
    }


}