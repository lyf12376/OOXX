package com.yi.xxoo.network.match

import com.yi.xxoo.bean.rankGame.CancelResponse
import com.yi.xxoo.bean.rankGame.PlayerSettlement
import com.yi.xxoo.bean.rankGame.RankGameRequest
import com.yi.xxoo.bean.rankGame.SubmitResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

interface MatchService {
    @POST("/game/rank/matching")
    fun matching(@Body rankGameRequest: RankGameRequest): Flow<MatchResponse>

    @POST("/game/rank/cancel")
    fun cancel(@Body rankGameRequest: RankGameRequest):CancelResponse

    @POST("/game/rank/submit")
    suspend fun submit(@Body playerSettlement: PlayerSettlement):SubmitResponse

}