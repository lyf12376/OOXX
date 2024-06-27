package com.yi.xxoo.network.worldBest

import com.yi.xxoo.Room.rank.worldBest.WorldBestRecord

data class WorldBestResponse(
    val code:Int,
    val message:String,
    val data:WorldBestRecord
)
