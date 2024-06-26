package com.yi.xxoo.Room.rank.passNum

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "PassNumRank",
    primaryKeys = ["rank"]
)
data class PassNumRank(
    val rank : Int,
    val userAccount:String,
    val userName : String,
    val passNum : Int
)