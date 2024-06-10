package com.yi.xxoo.Room.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "UserData",
    primaryKeys = ["account"]
)
data class User(
    val name : String,
    val account : String,//即为邮箱
    val password : String,
    val email : String,
    val coin:Int = 0,
    //头像存储路径
    val photo : String,
    //成就
    val achievement : String = "",
    //等级（maybe）
    val level : Int = 0,
    //完成游戏次数
    val gameTimes : Int = 0,
    //游戏时间
    val time : Int = 0,
    //最高分
    val bestRecord: String = "",
    //通关数
    val passNum:Int = 0,
)
