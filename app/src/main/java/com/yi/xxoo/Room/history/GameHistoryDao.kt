package com.yi.xxoo.Room.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameHistoryDao {
    @Insert
    suspend fun insertGameHistory(gameHistory: GameHistory)

    @Query("SELECT * FROM GameHistory WHERE userAccount = :userAccount")
    suspend fun getGameHistory(userAccount:String): List<GameHistory>
}