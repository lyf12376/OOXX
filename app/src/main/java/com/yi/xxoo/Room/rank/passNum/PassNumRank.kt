package com.yi.xxoo.Room.rank.passNum

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "PassNumRank",
)
data class PassNumRank(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val userAccount:String,
    val userName : String,
    val passNum : Int
)