package com.yi.xxoo.page.levelPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.game.GameDao
import com.yi.xxoo.Room.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LevelViewModel @Inject constructor(private val userDao: UserDao, private val gameDao: GameDao) : ViewModel() {
    private val _coin = MutableStateFlow(0)
    val coin: StateFlow<Int> = _coin

    init {
        // 初始化ViewModel时开始监听硬币数量的变化
        observeUserCoins()
        observeUserPassNum()
    }

    private fun observeUserCoins() {
        // 从Dao获取Flow<Int>，并在viewModelScope中收集
        viewModelScope.launch {
            userDao.getUserCoin(UserData.email).collect { coins ->
                // 更新StateFlow的值
                _coin.value = coins
            }
        }
    }

    private val _passNum = MutableStateFlow(0)
    val passNum: StateFlow<Int> = _passNum

    private fun observeUserPassNum() {
        viewModelScope.launch {
            userDao.getUserPassNum(UserData.email).collect { passNum ->
                _passNum.value = passNum
            }
        }
    }

    // 通过Dao更新用户硬币数量
    fun unLockGame(coin: Int) {
        viewModelScope.launch {
            userDao.updateUserCoin(UserData.email, coin)
            UserData.coin = coin
            userDao.updateUserPassNum(UserData.passNum+1,UserData.email)
            UserData.passNum += 1
        }
    }



    val gameList : Flow<List<String>> = getAllGamesInit()

    private fun getAllGamesInit(): Flow<List<String>> {
        return gameDao.getGameInit()
    }



}