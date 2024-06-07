package com.yi.xxoo.Room.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "User",
    primaryKeys = ["email"]
)
data class User(
    val name : String,
    val password : String,
    val email : String,
    //头像存储路径
    val photo : String,
    //成就
    val achievement : String,
    //等级（maybe）
    val level : Int,
    //完成游戏次数
    val gameTimes : Int,
    //游戏时间
    val time : Int,
    //最高分
    val bestRecord: String,
    //通关数
    val passNum:Int,
)