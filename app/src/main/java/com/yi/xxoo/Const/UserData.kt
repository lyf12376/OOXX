package com.yi.xxoo.Const

import com.yi.xxoo.Room.user.User
import com.yi.xxoo.utils.RoomUtils.stringToPersonalBestRecord

object UserData {
    var name : String = ""
    var account : String = "1357587070@qq.com"
    var password : String = ""
    var coin : Int = 0
    var email : String = ""
    var photo : String = ""
    var achievement : String = "000"
    var rank : Int = 0
    var rankWin:Int = 0
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
        rank = userData.rank
        rankWin = userData.rankWin
        gameTimes = userData.gameTimes
        time = userData.time
        bestRecord = userData.bestRecord.stringToPersonalBestRecord()
        passNum = userData.passNum
    }

    fun resetUserData()
    {
        name = ""
        account = ""
        password = ""
        coin = 0
        email = ""
        photo = ""
        achievement = "000"
        rank = 0
        rankWin = 0
        gameTimes = 0
        time = 0
        bestRecord = arrayListOf()
        passNum = 0
    }
}