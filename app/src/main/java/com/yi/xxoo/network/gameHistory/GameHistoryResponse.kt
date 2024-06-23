package com.yi.xxoo.network.gameHistory

import com.yi.xxoo.Room.history.GameHistory

data class GameHistoryResponse(
    val code: Int,
    val msg: String,
    val data: List<GameHistory>
)
