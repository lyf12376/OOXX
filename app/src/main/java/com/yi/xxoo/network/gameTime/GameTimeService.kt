package com.yi.xxoo.network.gameTime

import com.yi.xxoo.Room.rank.time.GameTimeRank
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface GameTimeService {
    @GET("game/rank/game_time")
    fun getGameTimeRank(): Flow<List<GameTimeRank>>


}