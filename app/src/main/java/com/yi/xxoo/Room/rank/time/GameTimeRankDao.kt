package com.yi.xxoo.Room.rank.time

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameTimeRankDao {
    @Insert
    suspend fun insertGameTimeRank(gameTimeRank: GameTimeRank)

    @Query("SELECT * FROM GameTimeRank")
    suspend fun getGameTimeRank(): List<GameTimeRank>

    @Query("update GameTimeRank set userName = :userName, time = :time where rank = :rank")
    suspend fun updateGameTimeRankByRank(rank: Int, userName: String, time: String)
}