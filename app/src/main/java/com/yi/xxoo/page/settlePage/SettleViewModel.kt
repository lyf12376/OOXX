package com.yi.xxoo.page.settlePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.Settlement
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.network.user.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettleViewModel @Inject constructor(
    private val userService: UserService
): ViewModel(){
    private val _firstFlagShow = MutableStateFlow(false)
    val firstFlagShow: StateFlow<Boolean> = _firstFlagShow

    private val _nextFlagShow = MutableStateFlow(false)
    val nextFlagShow: StateFlow<Boolean> = _nextFlagShow

    private val _isGiftShow = MutableStateFlow(false)
    val gifShow: StateFlow<Boolean> = _isGiftShow

    private val _exit = MutableStateFlow(false)
    val exit: StateFlow<Boolean> = _exit

    private val _time = MutableStateFlow(0L)
    val time = _time.asStateFlow()
    private var timerJob = viewModelScope.launch { }

    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    fun settle(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try {
                    if (Settlement.userPassTime + Settlement.userSubmitTimes * 5 - 5 < Settlement.enemyPassTime + Settlement.enemySubmitTimes * 5 - 5){
                        UserData.userRankWin ++
                        userService.updateUserRankWin(UserData.account,UserData.userRankWin)
                        if (UserData.userRank == 0 && UserData.userRankWin > 5){
                            UserData.userRank++
                        }
                        if (UserData.userRank == 1 && UserData.userRankWin > 10){
                            UserData.userRank++
                        }
                        userService.updateRank(UserData.account,UserData.userRank)
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    fun startTimer() {
        if (_isRunning.value) return
        _isRunning.value = true
        timerJob = viewModelScope.launch {
            while (_isRunning.value) {
                if (_time.value == 1L && !_firstFlagShow.value){
                    _firstFlagShow.value = true
                }
                if (_time.value == 5L && !_nextFlagShow.value){
                    _nextFlagShow.value = true
                }
                if (_time.value == 9L && !_isGiftShow.value) {
                    _isGiftShow.value = true
                }
                if (_time.value == 10L){
                    _exit.value = true
                }
                delay(1000)
                _time.value += 1
            }
        }
    }

    private fun resetTimer() {
        _isRunning.value = false
        timerJob.cancel()
        _time.value = 0L
    }
}