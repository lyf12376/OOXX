package com.yi.xxoo.Room.game

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Game")
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val difficulty : Int,
    val init:String,
    val target:String,
)