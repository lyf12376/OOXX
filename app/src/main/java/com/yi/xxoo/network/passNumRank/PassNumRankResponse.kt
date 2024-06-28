package com.yi.xxoo.network.passNumRank

import com.yi.xxoo.Room.rank.passNum.PassNumRank

data class PassNumRankResponse(
    val code: Int,
    val message:String,
    val data: List<PassNumRank>
)
