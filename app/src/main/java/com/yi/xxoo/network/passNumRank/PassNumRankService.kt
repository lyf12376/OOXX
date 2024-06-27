package com.yi.xxoo.network.passNumRank

import com.yi.xxoo.Room.rank.passNum.PassNumRank
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface PassNumRankService {
    @GET("game/rank/pass_num")
    fun getPassNumRank(): Flow<List<PassNumRank>>
}