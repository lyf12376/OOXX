package com.yi.xxoo.network.GameHistory

import com.yi.xxoo.Room.history.GameHistory
import retrofit2.http.GET
import retrofit2.http.Query

interface GameHistoryService {

    @GET("/game/history/histories")
    suspend fun getGameHistory(@Query("userAccount") userAccount: String): GameHistoryResponse


}