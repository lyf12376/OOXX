package com.yi.xxoo.bean.onlieGameHistory

data class OnlineGameHistory(
    val gameId: String,
    val grid: String,
    val init: String,
    val userAccount1: String,
    val userAccount2: String,
    val userNickName1: String,
    val userNickName2: String,
    val startTime: String,
    val gameTime1:Int,
    val gameTime2:Int,
    val winner: String
)


