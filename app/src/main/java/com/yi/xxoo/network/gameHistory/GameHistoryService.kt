package com.yi.xxoo.network.gameHistory

import com.yi.xxoo.Room.history.GameHistory
import com.yi.xxoo.bean.login.CommonResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GameHistoryService {

    @GET("/game/history/histories")
    fun getGameHistory(@Query("userAccount") userAccount: String): Flow<GameHistoryResponse>


    @POST("/game/history/insert")
    fun insertGameHistory(@Body gameHistory: GameHistory): CommonResponse




}