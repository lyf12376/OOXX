package com.yi.xxoo.network.match

import com.yi.xxoo.bean.rankGame.RankGameResult

data class MatchResponse(
    val code: Int,
    val msg: String,
    val data: List<RankGameResult>
)

