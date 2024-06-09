package com.yi.xxoo.Room.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    fun createUser(user: User): Long

    @Query("SELECT * FROM User WHERE email = :email")
    fun getUserByEmail(email: String): User

    @Query("SELECT * FROM User WHERE name = :name and password = :password")
    fun login(name: String,password: String): User

    @Update
    fun updateUser(user: User)

    @Query("UPDATE User SET name = :name WHERE email = :email")
    fun updateUserName(email: String, name: String)

    @Query("UPDATE User SET password = :password WHERE email = :email")
    fun updateUserPassword(email: String, password: String)

    @Query("UPDATE User SET photo = :photo WHERE email = :email")
    fun updateUserPhoto(email: String, photo: String)

    @Query("UPDATE User SET achievement = :achievement WHERE email = :email")
    fun updateUserAchievement(email: String, achievement: String)

    @Query("UPDATE User SET level = :level WHERE email = :email")
    fun updateUserLevel(email: String, level: Int)

    @Query("UPDATE User SET gameTimes = :gameTimes WHERE email = :email")
    fun updateUserGameTimes(email: String, gameTimes: Int)

    @Query("UPDATE User SET time = :time WHERE email = :email")
    fun updateUserTime(email: String, time: Int)

    @Query("SELECT bestRecord FROM User WHERE email = :email")
    fun getUserBestRecord(email: String): String
}