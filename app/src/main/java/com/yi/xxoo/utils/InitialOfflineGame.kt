package com.yi.xxoo.utils

import android.content.Context
import com.yi.xxoo.Room.game.Game
import com.yi.xxoo.Room.game.GameDao
import com.yi.xxoo.Room.game.GameDatabase

object InitialOfflineGame {
    val init = listOf(
        "XOXOX OXXO OXOO  XXOOXX  XX OXO OXOX",
        "OOX OXXO XOXO XOOOXOXXOX  OXOX XXO OXOX OOXO  XXXX  OXOOXXOOXOOX",
        "XOX    OXXOOX  OOOXXOXOXXX XOOXOO XOXXOXO  XOXXO  XOXOOX  OXOXOX"
        )
    private val target = listOf(
        "XOXOXOOXXOXOXOOXOXXOOXXOOXXOOXOXOXOX",
        "OOXXOXXOXXOXOOXOOOXOXXOXOXOXOXOXXOXOXOXOOOXOXOXXXXOXOXOOXXOOXOOX",
        "XOXOXOXOXXOOXOXOOOXXOXOXXXOXOOXOOOXOXXOXOXOXOXXOXOXOXOOXOXOXOXOX"
        )
    val difficulty = listOf(1,2,3)
    suspend fun initialOfflineGame(context: Context){
        val gameDao = GameDatabase.getDatabase(context).gameDao()
        for (i in init.indices){
            gameDao.createGame(
                Game(
                    difficulty = difficulty[i],
                    init = init[i],
                    target = target[i]
                )
            )
        }
    }

    fun isRoomDatabaseExist(context: Context): Boolean {
        val dbFile = context.getDatabasePath("Game")
        return dbFile.exists()
    }
}