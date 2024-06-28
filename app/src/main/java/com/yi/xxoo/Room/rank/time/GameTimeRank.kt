package com.yi.xxoo.Room.rank.time

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "GameTimeRank",
    primaryKeys = ["id"]
)
data class GameTimeRank(
    val id : Int,
    val userAccount:String,
    val userName : String,
    val gameTime : Int
)
