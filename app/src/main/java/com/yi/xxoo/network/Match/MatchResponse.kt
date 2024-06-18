package com.yi.xxoo.network.Match

import com.yi.xxoo.bean.rankGame.RankGameResult

data class MatchResponse(
    val code: Int,
    val msg: String,
    val data: List<RankGameResult>
)

