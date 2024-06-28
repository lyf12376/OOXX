package com.yi.xxoo.Room.game

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert
    suspend fun createGame(game: Game): Long

    @Query("SELECT * FROM Game WHERE id = :id")
    suspend fun getGameById(id: Long): Game

    @Query("SELECT * FROM Game")
    fun getAllGames(): Flow<List<Game>>

    @Query("select init from Game")
    fun getGameInit(): Flow<List<String>>

    @Query("select count(*) from Game")
    suspend fun getGamesSize():Int


}