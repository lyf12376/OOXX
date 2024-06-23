package com.yi.xxoo.page.preparePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrepareViewModel @Inject constructor() : ViewModel(){
    private val _firstFlagShow = MutableStateFlow(false)
    val firstFlagShow:StateFlow<Boolean> = _firstFlagShow

    private val _nextFlagShow = MutableStateFlow(false)
    val nextFlagShow:StateFlow<Boolean> = _nextFlagShow

    private val _gifShow = MutableStateFlow(false)
    val gifShow:StateFlow<Boolean> = _gifShow

    private val _loading = MutableStateFlow(false)
    val loading:StateFlow<Boolean> = _loading

    private val _time = MutableStateFlow(0L)
    val time = _time.asStateFlow()
    private var timerJob = viewModelScope.launch { }

    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

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
                if (_time.value == 9L && !_gifShow.value) {
                    _gifShow.value = true
                }
                if (_time.value == 11L && !_loading.value){
                    _loading.value = true
                    resetTimer()
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