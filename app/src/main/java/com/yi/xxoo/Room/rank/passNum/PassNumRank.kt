package com.yi.xxoo.Room.rank.passNum

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "PassNumRank",
    primaryKeys = ["id"]
)
data class PassNumRank(
    val id : Int,
    val userAccount:String,
    val userName : String,
    val passNum : Int
)