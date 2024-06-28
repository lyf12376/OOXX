package com.yi.xxoo.network.onlineGameHistory

import com.yi.xxoo.Room.history.GameHistory
import com.yi.xxoo.bean.onlieGameHistory.OnlineGameHistory
import com.yi.xxoo.network.gameHistory.GameHistoryResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface OnlineGameHistoryService {
    @GET("/game/rank/onlineGameHistory")
    fun getOnlineGameHistory(@Query("gameId") gameId:String): Flow<OnlineGameHistory>

    @GET("/game/rank/onlineGameHistoryPre")
    fun getOnlineGameHistoryPre(@Query("userAccount") userAccount: String): Flow<GameHistoryResponse>
}