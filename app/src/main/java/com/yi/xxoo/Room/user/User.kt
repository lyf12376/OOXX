package com.yi.xxoo.Room.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yi.xxoo.Room.converter.UserConverters

@Entity(
    tableName = "UserData",
    primaryKeys = ["account"]
)
data class User(
    val name : String = "offline",
    val account : String = "offline",//即为邮箱
    val password : String = "offline",
    val email : String = "offline",
    val coin:Int = 0,
    //头像存储路径
    val photo : String = "",
    //成就   存储例如"000","111"  0代表未完成，1代表完成
    val achievement : String = "000",
    //排位等级（maybe）
    val rank : Int = 0,
    val rankWin :Int = 0,
    //完成游戏次数
    val gameTimes : Int = 0,
    //游戏时间
    val time : Int = 0,
    //最高分
    /*
    存储格式如：
    "200,500,120"
    按每一关的顺序排列，单位为秒，用逗号分隔
    */
    val bestRecord: String = "",
    //通关数
    val passNum:Int = 0
)
