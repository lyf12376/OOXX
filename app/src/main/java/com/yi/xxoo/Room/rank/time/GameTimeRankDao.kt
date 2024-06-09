package com.yi.xxoo.Room.rank.time

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameTimeRankDao {
    @Insert
    fun insertGameTimeRank(gameTimeRank: GameTimeRank)

    @Query("SELECT * FROM GameTimeRank")
    fun getGameTimeRank(rank: Int): List<GameTimeRank>

    @Query("update GameTimeRank set userName = :userName, time = :time where rank = :rank")
    fun updateGameTimeRankByRank(rank: Int, userName: String, time: String)
}