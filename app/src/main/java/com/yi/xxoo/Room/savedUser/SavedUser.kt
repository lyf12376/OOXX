package com.yi.xxoo.Room.savedUser

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "savedUsers", indices = [Index(value = ["account"],unique = true)])
data class SavedUser (
    @PrimaryKey(true)
    val id : Long = 0,
    val account : String,
    val password : String
)