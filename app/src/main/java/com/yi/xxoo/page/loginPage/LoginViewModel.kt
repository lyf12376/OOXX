package com.yi.xxoo.page.loginPage

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.GameMode
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.savedUser.SavedUser
import com.yi.xxoo.Room.savedUser.SavedUserDao
import com.yi.xxoo.Room.user.User
import com.yi.xxoo.Room.user.UserDao
import com.yi.xxoo.network.user.UserService
import com.yi.xxoo.utils.NetWorkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val savedUserDao: SavedUserDao,private val userDao: UserDao,private val userService: UserService): ViewModel(){
    private val _network = MutableStateFlow(GameMode.isNetworkEnabled)
    val network:StateFlow<Boolean> = _network

    val savedUserList: Flow<List<SavedUser>> = getSavedUser()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess:StateFlow<Boolean> = _loginSuccess

    private val _loginFailed = MutableStateFlow(false)
    val loginFailed:StateFlow<Boolean> = _loginFailed

    private val _offlineGame = MutableStateFlow(false)
    val offlineGame:StateFlow<Boolean> = _offlineGame

    private val _startOfflineGame = MutableStateFlow(false)
    val startOfflineGame:StateFlow<Boolean> = _startOfflineGame

    private val _registerOfflineAccount = MutableStateFlow(false)
    val registerOfflineAccount:StateFlow<Boolean> = _registerOfflineAccount


    private fun setNetWork()
    {
        _network.value = true
    }

    fun reConnect(context: Context){
        _network.value = true
        _network.value = NetWorkUtils.isNetworkAvailable(context)
    }

    fun offlineMode(){
        setNetWork()
        viewModelScope.launch {
            val user = userDao.login("offline","offline")
            if (user!=null){
                _startOfflineGame.value = true
                UserData.setUser(user)
            }else{
                _registerOfflineAccount.value = true
                userDao.createUser(User())
                UserData.account = "offline"
            }

        }

    }


    fun isLogin(account: String, password: String) {

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        val loginResponse = userService.login(account, password)
                        if (loginResponse.code == 200) {
                            _loginSuccess.value = true
                            UserData.setUser(loginResponse.data!!)
                            if (userDao.isExist(loginResponse.data.account) == null)
                                userDao.createUser(loginResponse.data)
                            else
                                userDao.updateUser(loginResponse.data)
                        } else {
                            _loginFailed.value = true
                        }
                    } catch (e: Exception) {
                        Log.d("TAG", "isLogin: ${e.message}")
                    }
                }
            }

    }

    fun saveUser(savedUser: SavedUser) {
        viewModelScope.launch {
            savedUserDao.saveUsers(savedUser)
        }
    }

    private fun getSavedUser(): Flow<List<SavedUser>> {
        return savedUserDao.getSavedUsers()
    }

    fun deleteUser(account: String) {
        viewModelScope.launch {
            savedUserDao.unRemember(account)
        }
    }

    fun setLoginFailed(){
        _loginFailed.value = false
    }
}