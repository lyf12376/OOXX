package com.yi.xxoo.Room.rank.passNum

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PassNumRank::class], version = 1, exportSchema = false)
abstract class PassNumRankDatabase : RoomDatabase(){
    abstract fun passNumRankDao(): PassNumRankDao

    companion object {
        @Volatile
        private var INSTANCE: PassNumRankDatabase? = null

        fun getDatabase(context: Context): PassNumRankDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PassNumRankDatabase::class.java,
                    "PassNumRank"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}