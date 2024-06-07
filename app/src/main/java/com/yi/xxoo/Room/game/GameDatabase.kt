package com.yi.xxoo.Room.game

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Game::class], version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase(){
    abstract fun gameDao(): GameDao
    companion object{
        @Volatile
        private var INSTANCE: GameDatabase? = null
        fun getDatabase(context: Context): GameDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "Game"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}