package com.yi.xxoo.Room.history

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameHistory::class], version = 1)
abstract class GameHistoryDatabase : RoomDatabase(){
    abstract fun gameHistoryDao(): GameHistoryDao

    companion object{
        @Volatile
        private var INSTANCE: GameHistoryDatabase? = null

        fun getDatabase(context: Context): GameHistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameHistoryDatabase::class.java,
                    "GameHistory"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}