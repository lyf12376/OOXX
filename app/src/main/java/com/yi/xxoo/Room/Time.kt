package com.yi.xxoo.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LoginTime")
data class LoginTime(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val time : String
)