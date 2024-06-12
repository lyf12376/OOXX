package com.yi.xxoo.Room.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import androidx.room.Update
import com.yi.xxoo.Room.converter.UserConverters

@Dao
interface UserDao {
    @Insert
    suspend fun createUser(user: User): Long

    @Query("SELECT * FROM UserData WHERE email = :email")
    suspend fun getUserByEmail(email: String): User

    @Query("SELECT * FROM UserData WHERE name = :name and password = :password")
    suspend fun login(name: String, password: String): User?

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE UserData SET coin = :coin WHERE email = :email")
    suspend fun updateUserCoin(email: String, coin: Int)

    @Query("UPDATE UserData SET name = :name WHERE email = :email")
    suspend fun updateUserName(email: String, name: String)

    @Query("UPDATE UserData SET password = :password WHERE email = :email")
    suspend fun updateUserPassword(email: String, password: String)

    @Query("UPDATE UserData SET photo = :photo WHERE email = :email")
    suspend fun updateUserPhoto(email: String, photo: String)

    @Query("UPDATE UserData SET achievement = :achievement WHERE email = :email")
    suspend fun updateUserAchievement(email: String, achievement: String)

    @Query("UPDATE UserData SET level = :level WHERE email = :email")
    suspend fun updateUserLevel(email: String, level: Int)

    @Query("UPDATE UserData SET gameTimes = :gameTimes WHERE email = :email")
    suspend fun updateUserGameTimes(email: String, gameTimes: Int)

    @Query("UPDATE UserData SET time = :time WHERE email = :email")
    suspend fun updateUserTime(email: String, time: Int)

    @Query("UPDATE UserData SET bestRecord = :bestRecord WHERE email = :email")
    suspend fun updateBestRecordByEmail(email: String, bestRecord: String)

    @Query("SELECT bestRecord FROM UserData WHERE email = :email")
    suspend fun getUserBestRecord(email: String): String
}