package com.yi.xxoo.Room.game

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameDao {
    @Insert
    fun createGame(game: Game): Long

    @Query("SELECT * FROM Game WHERE id = :id")
    fun getGameById(id: Long): Game
}