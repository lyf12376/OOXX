package com.yi.xxoo.page.levelPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.GameMode
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.game.GameDao
import com.yi.xxoo.Room.user.UserDao
import com.yi.xxoo.network.user.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LevelViewModel @Inject constructor(private val userDao: UserDao, private val gameDao: GameDao,private val userService: UserService) : ViewModel() {
    private val _coin = MutableStateFlow(0)
    val coin: StateFlow<Int> = _coin

    val errorList = listOf("金币不足，无法解锁游戏","未解锁关卡，无法进行游戏")
    private val _errorStatus = MutableStateFlow(0)
    val errorStatus:StateFlow<Int> = _errorStatus

    init {
        // 初始化ViewModel时开始监听硬币数量的变化
        observeUserCoins()
        observeUserPassNum()
    }

    fun setError2()
    {
        _errorStatus.value = 2
    }

    fun knowErr(){
        _errorStatus.value = 0
    }

    private fun observeUserCoins() {
        // 从Dao获取Flow<Int>，并在viewModelScope中收集
        viewModelScope.launch {
            userDao.getUserCoin(UserData.account).collect { coins ->
                // 更新StateFlow的值
                _coin.value = coins
            }
        }
    }

    private val _passNum = MutableStateFlow(0)
    val passNum: StateFlow<Int> = _passNum

    private fun observeUserPassNum() {
        viewModelScope.launch {
            userDao.getUserPassNum(UserData.account).collect { passNum ->
                _passNum.value = passNum
            }
        }
    }

    // 通过Dao更新用户硬币数量
    fun unLockGame(coin: Int) {
        if (UserData.coin >= 20) {
            viewModelScope.launch {
                userDao.updateUserCoin(UserData.account, coin)
                UserData.coin = coin
                userDao.updateUserPassNum(UserData.passNum + 1, UserData.account)
                UserData.passNum += 1
                if (GameMode.isNetworkEnabled) {
                    withContext(Dispatchers.IO) {
                        try {
                            userService.updateUserCoin(UserData.account, coin)
                            userService.updateUserPassNum(UserData.account, UserData.passNum)
                        } catch (e: Exception) {
                            Log.d("TAG", "unLockGame: ${e.message}")
                        }
                    }
                }
            }
        }else{
            _errorStatus.value = 1
        }
    }



    val gameList : Flow<List<String>> = getAllGamesInit()

    private fun getAllGamesInit(): Flow<List<String>> {
        return gameDao.getGameInit()
    }



}