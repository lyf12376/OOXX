package com.yi.xxoo.Room.rank.passNum

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PassNumRankDao {
    @Insert
    suspend fun insert(passNumRank: PassNumRank)

    @Query("SELECT * FROM passNumRank")
    fun getPassNumRank(): Flow<List<PassNumRank>>

    @Query("update passNumRank set userName = :userName, passNum = :passNum where id = :rank")
    suspend fun updatePassNumRankByRank(rank: Int, userName: String, passNum: String)

}