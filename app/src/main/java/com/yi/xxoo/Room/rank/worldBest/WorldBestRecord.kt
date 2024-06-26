package com.yi.xxoo.Room.rank.worldBest

import androidx.room.Entity

@Entity(
    tableName = "WorldBestRecord",
    primaryKeys = ["whichGame"]
)
data class  WorldBestRecord(
    val whichGame : Int,
    val userAccount:String,
    val userName : String,
    val time : String
)
