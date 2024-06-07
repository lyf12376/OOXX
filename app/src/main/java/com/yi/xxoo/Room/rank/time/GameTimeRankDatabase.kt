package com.yi.xxoo.Room.rank.time

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameTimeRank::class], version = 1, exportSchema = false)
abstract class GameTimeRankDatabase : RoomDatabase(){
    abstract fun gameTimeRankDao(): GameTimeRankDao

    companion object{
        @Volatile
        private var INSTANCE: GameTimeRankDatabase? = null

        fun getDatabase(context: Context): GameTimeRankDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameTimeRankDatabase::class.java,
                    "GameTimeRank"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}