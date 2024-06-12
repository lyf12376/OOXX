package com.yi.xxoo.Const

import com.yi.xxoo.Room.user.User
import com.yi.xxoo.utils.RoomUtils.stringToPersonalBestRecord

object UserData {
    var name : String = ""
    var account : String = ""
    var password : String = ""
    var coin : Int = 0
    var email : String = ""
    var photo : String = ""
    var achievement : String = ""
    var level : Int = 0
    var gameTimes : Int = 0
    var time : Int = 0
    var bestRecord: ArrayList<Int> = arrayListOf()
    var passNum:Int = 0

    fun setUser(userData: User) {
        name = userData.name
        account = userData.account
        password = userData.password
        email = userData.email
        coin = userData.coin
        photo = userData.photo
        achievement = userData.achievement
        level = userData.level
        gameTimes = userData.gameTimes
        time = userData.time
        bestRecord = userData.bestRecord.stringToPersonalBestRecord()
        passNum = userData.passNum
    }
}