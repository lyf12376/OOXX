package com.yi.xxoo.network.passNumRank

import com.yi.xxoo.Room.rank.passNum.PassNumRank
import com.yi.xxoo.bean.login.CommonResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PassNumRankService {
    @GET("game/passRank/pass_num")
    fun getPassNumRank(): Flow<PassNumRankResponse>

    @POST("game/passRank/update")
    suspend fun updatePassNumRank(@Body passNumRank: PassNumRank): CommonResponse
}