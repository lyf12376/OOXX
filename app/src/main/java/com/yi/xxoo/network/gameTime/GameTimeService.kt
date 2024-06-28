package com.yi.xxoo.network.gameTime

import com.yi.xxoo.Room.rank.time.GameTimeRank
import com.yi.xxoo.bean.login.CommonResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GameTimeService {
    @GET("game/gameTimeRank/game_time")
    fun getGameTimeRank(): Flow<GameTimeResponse>

    @POST("game/gameTimeRank/update")
    fun updateGameTimeRank(@Body gameTimeRank: GameTimeRank): CommonResponse

}