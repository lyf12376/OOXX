package com.yi.xxoo.Room.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    fun createUser(user: User): Long

    @Query("SELECT * FROM UserData WHERE email = :email")
    fun getUserByEmail(email: String): User

    @Query("SELECT * FROM UserData WHERE name = :name and password = :password")
    fun login(name: String,password: String): User?

    @Update
    fun updateUser(user: User)

    @Query("UPDATE UserData SET coin = :coin WHERE email = :email")
    fun updateUserCoin(email: String, coin: Int)

    @Query("UPDATE UserData SET name = :name WHERE email = :email")
    fun updateUserName(email: String, name: String)

    @Query("UPDATE UserData SET password = :password WHERE email = :email")
    fun updateUserPassword(email: String, password: String)

    @Query("UPDATE UserData SET photo = :photo WHERE email = :email")
    fun updateUserPhoto(email: String, photo: String)

    @Query("UPDATE UserData SET achievement = :achievement WHERE email = :email")
    fun updateUserAchievement(email: String, achievement: String)

    @Query("UPDATE UserData SET level = :level WHERE email = :email")
    fun updateUserLevel(email: String, level: Int)

    @Query("UPDATE UserData SET gameTimes = :gameTimes WHERE email = :email")
    fun updateUserGameTimes(email: String, gameTimes: Int)

    @Query("UPDATE UserData SET time = :time WHERE email = :email")
    fun updateUserTime(email: String, time: Int)

    @Query("SELECT bestRecord FROM UserData WHERE email = :email")
    fun getUserBestRecord(email: String): String
}