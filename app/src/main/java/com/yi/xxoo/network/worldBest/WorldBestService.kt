package com.yi.xxoo.network.worldBest

import com.yi.xxoo.Room.rank.worldBest.WorldBestRecord
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WorldBestService {
    @GET("worldBest/getWorldBest")
    suspend fun getWorldBestByLevel(@Query("level") level:Int):WorldBestResponse

    @POST("worldBest/insertWorldBest")
    suspend fun insertWorldBest(@Body worldBestRecord: WorldBestRecord)
}