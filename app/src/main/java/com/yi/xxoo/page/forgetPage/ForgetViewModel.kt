package com.yi.xxoo.page.forgetPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.bean.register.RegisterRequest
import com.yi.xxoo.network.user.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ForgetViewModel @Inject constructor(
    private val userService: UserService
):ViewModel() {

    private val _sendFailed = MutableStateFlow(false)
    val sendFailed: StateFlow<Boolean> = _sendFailed

    private val _sendSuccess = MutableStateFlow(false)
    val sendSuccess: StateFlow<Boolean> = _sendSuccess

    private val _error = MutableStateFlow(0)
    val error :StateFlow<Int> = _error

    private val _updateStatus = MutableStateFlow(0)
    val updateStatus = _updateStatus.asStateFlow()

    fun sendMail(email: String) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val userResponse = userService.sendMail(email)
                    if (userResponse.code == 200) {
                        _sendSuccess.value = true
                    } else
                        _sendFailed.value = true
                } catch (e: Exception) {
                    _sendFailed.value = false
                    Log.d("TAG", "sendMail: ${e.message}")
                }
            }
        }
    }

    fun register(email: String, password: String, code: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val userResponse = userService.updatePassword(email,password,code)
                    if (userResponse.code == 200){
                        _updateStatus.value = 1
                    }
                    if (userResponse.code == 401) {
                        _updateStatus.value = 2
                    }
                    if (userResponse.code == 400){
                        _updateStatus.value = 3
                    }
                } catch (e: Exception) {
                    _updateStatus.value = 4
                    Log.d("TAG", "register: ${e.message}")
                }
            }
        }
    }

    fun setError()
    {
        _updateStatus.value = 0
    }

    fun setSendFailed() {
        _sendFailed.value = false
    }
}