package com.yi.xxoo.network.gameTime

import com.yi.xxoo.Room.rank.time.GameTimeRank

data class GameTimeResponse(
    val code: Int,
    val message:String,
    val data: List<GameTimeRank>
)
