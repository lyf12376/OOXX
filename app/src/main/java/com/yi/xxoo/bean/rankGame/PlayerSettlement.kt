package com.yi.xxoo.bean.rankGame

import RankGame


data class PlayerSettlement (
    val gameId: String,
    val rankGame: RankGame,
    val status: Int,
    val time:Int,
    val records: List<SubmissionRecord>
)
