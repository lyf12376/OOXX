package com.yi.xxoo.network.Match

import com.yi.xxoo.bean.rankGame.RankGameRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

interface MatchService {
    @POST("/game/rank/matching")
    fun matching(@Body rankGameRequest: RankGameRequest): Flow<MatchResponse>
}