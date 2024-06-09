package com.yi.xxoo.Room.history

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.yi.xxoo.Room.user.User

@Entity(
    tableName = "GameHistory",
    foreignKeys = [
        (ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("account"),
            childColumns = arrayOf("userAccount"),
            onDelete = CASCADE
        ))
    ],
    indices = [
        Index(value = ["userAccount"])
    ]
)
data class GameHistory(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val userAccount : String,
    val startTime : String,
    val gameTime : String,
    val state : String
)
