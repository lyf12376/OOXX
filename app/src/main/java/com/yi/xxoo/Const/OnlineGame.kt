package com.yi.xxoo.Const

import android.util.Log

object OnlineGame {
    var gameId = ""
    var init = ""
    var target = ""
    var enemyName = ""
    var enemyRank = 0

    fun setOnlineGame(gameId:String,init:String,target: String){
        OnlineGame.gameId = gameId
        OnlineGame.init = init
        OnlineGame.target = target
        Log.d("TAG", "setOnlineGame: $init")
        Log.d("TAG", "setOnlineGame: $target")
    }

    fun gameOver(){
        init = ""
        target = ""
        enemyName = ""
    }
}

