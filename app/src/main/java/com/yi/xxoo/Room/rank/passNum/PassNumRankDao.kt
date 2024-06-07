package com.yi.xxoo.Room.rank.passNum

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PassNumRankDao {
    @Insert
    fun insert(passNumRank: PassNumRank)

    @Query("SELECT * FROM passNumRank")
    fun getPassNumRank(): List<PassNumRank>

    @Query("update passNumRank set userName = :userName, passNum = :passNum where rank = :rank")
    fun updatePassNumRankByRank(rank: Int, userName: String, passNum: String)

}