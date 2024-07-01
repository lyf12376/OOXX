package com.yi.xxoo.Room.rank.time

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "GameTimeRank",
)
data class GameTimeRank(
    @PrimaryKey(true)
    val id : Long,
    val userAccount:String,
    val userName : String,
    val gameTime : Int
)
