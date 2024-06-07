package com.yi.xxoo.Room

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface TimeDao {
    @Insert
    suspend fun saveTime(time: LoginTime):Long
}