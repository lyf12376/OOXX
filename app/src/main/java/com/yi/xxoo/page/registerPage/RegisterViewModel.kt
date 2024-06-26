package com.yi.xxoo.page.registerPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.user.User
import com.yi.xxoo.Room.user.UserDao
import com.yi.xxoo.bean.register.RegisterRequest
import com.yi.xxoo.network.user.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userDao: UserDao,private val userService: UserService) : ViewModel(){

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess: StateFlow<Boolean> = _registerSuccess

    private val _registerFailed = MutableStateFlow(false)
    val registerFailed: StateFlow<Boolean> = _registerFailed

    private val _sendFailed = MutableStateFlow(false)
    val sendFailed: StateFlow<Boolean> = _sendFailed

    private val _sendSuccess = MutableStateFlow(false)
    val sendSuccess: StateFlow<Boolean> = _sendSuccess

    fun sendMail(email: String) {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO){
                    val userResponse = userService.sendMail(email)
                    if (userResponse.code == 200){
                        _sendSuccess.value = true
                    }else
                        _sendFailed.value = true
                }
            }

        }catch (e:Exception){
            Log.d("TAG", "sendMail: ${e.message}")
        }
    }

    suspend fun register(email: String, password: String,code:String) {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO){
                    val userResponse = userService.register(RegisterRequest( email,password,code))
                    if (userResponse.code == 200){
                        _registerSuccess.value = true
                        UserData.account = email
                        UserData.email = email
                    }
                }
            }
        }catch (e:Exception){
            Log.d("TAG", "register: ${e.message}")
        }
    }


    fun setSendFailed(){
        _sendFailed.value = false
    }

    fun setRegisterFailed(){
        _registerFailed.value = false
    }



}