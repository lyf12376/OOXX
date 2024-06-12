package com.yi.xxoo.Room.rank.passNum

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PassNumRankDao {
    @Insert
    suspend fun insert(passNumRank: PassNumRank)

    @Query("SELECT * FROM passNumRank")
    suspend fun getPassNumRank(): List<PassNumRank>

    @Query("update passNumRank set userName = :userName, passNum = :passNum where rank = :rank")
    suspend fun updatePassNumRankByRank(rank: Int, userName: String, passNum: String)

}