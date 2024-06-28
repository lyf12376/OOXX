package com.yi.xxoo.Room.rank.time

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameTimeRankDao {
    @Insert
    suspend fun insertGameTimeRank(gameTimeRank: GameTimeRank)

    @Query("SELECT * FROM GameTimeRank")
    fun getGameTimeRank(): Flow<List<GameTimeRank>>

    @Query("update GameTimeRank set userName = :userName, gameTime = :time where id = :rank")
    suspend fun updateGameTimeRankByRank(rank: Int, userName: String, time: String)
}