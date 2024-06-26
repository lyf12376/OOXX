package com.yi.xxoo.Room.rank.time

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "GameTimeRank",
    primaryKeys = ["rank"]
)
data class GameTimeRank(
    val rank : Int,
    val userAccount:String,
    val userName : String,
    val time : String
)
