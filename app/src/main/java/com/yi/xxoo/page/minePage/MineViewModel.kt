package com.yi.xxoo.page.minePage

import androidx.lifecycle.ViewModel
import com.yi.xxoo.Const.UserData
import com.yi.xxoo.Room.game.Game
import com.yi.xxoo.Room.game.GameDao
import com.yi.xxoo.Room.user.User
import com.yi.xxoo.Room.user.UserDao
import com.yi.xxoo.network.gameHistory.GameHistoryResponse
import com.yi.xxoo.network.gameHistory.GameHistoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MineViewModel @Inject constructor(
    private val userDao: UserDao,
    private val gameDao: GameDao,
    private val historyService: GameHistoryService
) : ViewModel() {
    suspend fun creatUser() {
        userDao.createUser(
            User(
                "name",
                "account",
                "password",
                "account",
                photo = "",
                bestRecord = "123,45"
            )
        )
    }

    suspend fun getUserData() {
        UserData.setUser(userDao.getUserByEmail("account"))
    }

    suspend fun creatGame() {
        gameDao.createGame(
            Game(
                difficulty = 3,
                init = "XOXOXOOXXOXOXOO  XXOOXX  XXOOXOXOXOX",
                target = "XOXOXOOXXOXOXOOXOXXOOXXOOXXOOXOXOXOX"
            )
        )
    }

    suspend fun getUserHistory(): GameHistoryResponse {
        return historyService.getGameHistory(UserData.account)
    }
}