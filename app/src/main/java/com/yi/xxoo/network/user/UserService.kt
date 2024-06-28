package com.yi.xxoo.network.user

import com.yi.xxoo.bean.login.LoginResponse
import com.yi.xxoo.bean.login.CommonResponse
import com.yi.xxoo.bean.register.RegisterRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface UserService {
    @GET("user/login")
    suspend fun login(
        @Query("userAccount") account:String,
        @Query("password") password:String
    ): LoginResponse

    @POST("user/register")
    suspend fun register(@Body registerRequest: RegisterRequest): CommonResponse

    @GET("user/sendMail")
    suspend fun sendMail(@Query("email") email:String): CommonResponse

    @POST("user/updateAvatar")
    @Multipart
    suspend fun updateUserAvatar(@Header("account") account:String,@Part avatar:MultipartBody.Part): CommonResponse

    @GET("user/updateName")
    suspend fun updateName(@Query("userAccount") account:String,@Query("name") name:String):CommonResponse

    @GET("user/updatePassword")
    suspend fun updatePassword(@Query("userAccount") account:String,@Query("password") password:String):CommonResponse

    @GET("user/updateCoin")
    suspend fun updateUserCoin(@Query("userAccount") account:String,@Query("coin") coin:Int):CommonResponse

    @GET("user/updatePassNum")
    suspend fun updateUserPassNum(@Query("userAccount") account:String,@Query("passNum") passNum:Int):CommonResponse

    @GET("user/updateAchievement")
    suspend fun updateAchievement(@Query("userAccount") account:String,@Query("achievement") achievement:String):CommonResponse

    @GET("user/updateRank")
    suspend fun updateRank(@Query("userAccount") account:String,@Query("rank") rank:Int):CommonResponse

    @GET("user/updateGameTimes")
    suspend fun updateGameTimes(@Query("userAccount") account:String,@Query("gameTimes") gameTimes:Int):CommonResponse

    @GET("user/updateTime")
    suspend fun updateTime(@Query("userAccount") account:String,@Query("time") time:Int):CommonResponse

    @GET("user/updateBestRecord")
    suspend fun updateBestRecord(@Query("userAccount") account:String,@Query("bestRecord") bestRecord:String):CommonResponse

    @GET("user/updateUserRankWin")
    suspend fun updateUserRankWin(@Query("userAccount") account:String,@Query("userRankWin") userRankWin:Int):CommonResponse

}