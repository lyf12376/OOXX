package com.yi.xxoo.Room.rank.worldBest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WorldBestRecordDao {
    @Insert
    fun insertWorldBestRecord(worldBestRecord: WorldBestRecord)

    @Query("SELECT * FROM WorldBestRecord")
    fun getWorldBestRecordByGame(): Flow<List<WorldBestRecord>>

    @Query("update WorldBestRecord set userName = :userName, time = :time where whichGame = :whichGame")
    fun updateWorldBestRecordByGame(whichGame: Int, userName: String, time: String)
}