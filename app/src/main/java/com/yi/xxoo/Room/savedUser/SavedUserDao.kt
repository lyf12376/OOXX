package com.yi.xxoo.Room.savedUser

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUsers(savedUser: SavedUser):Long

    @Query("DELETE FROM savedUsers WHERE account = :account")
    fun unRemember(account:String)

    @Query("SELECT * FROM savedUsers")
    fun getSavedUsers(): Flow<List<SavedUser>>

}