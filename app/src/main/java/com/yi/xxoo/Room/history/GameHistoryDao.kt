package com.yi.xxoo.Room.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameHistoryDao {
    @Insert
    suspend fun insertGameHistory(gameHistory: GameHistory)

    @Query("SELECT * FROM GameHistory WHERE userAccount = :userAccount")
    fun getGameHistory(userAccount:String): Flow<List<GameHistory>>
}