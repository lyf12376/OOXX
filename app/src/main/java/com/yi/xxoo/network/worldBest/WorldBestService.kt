package com.yi.xxoo.network.worldBest

import com.yi.xxoo.Room.rank.worldBest.WorldBestRecord
import com.yi.xxoo.bean.login.CommonResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WorldBestService {
    @GET("worldBest/getWorldBest")
     fun getWorldBestByLevel(@Query("level") level:Int):Flow<WorldBestResponse>

    @POST("worldBest/insertWorldBest")
    suspend fun insertWorldBest(@Body worldBestRecord: WorldBestRecord):CommonResponse
}