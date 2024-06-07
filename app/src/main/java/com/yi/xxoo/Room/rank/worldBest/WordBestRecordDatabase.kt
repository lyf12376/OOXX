package com.yi.xxoo.Room.rank.worldBest

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WorldBestRecord::class], version = 1)
abstract class WordBestRecordDatabase : RoomDatabase(){
    abstract fun wordBestRecordDao(): WordBestRecordDao

    companion object{
        @Volatile
        private var INSTANCE: WordBestRecordDatabase? = null
        fun getDatabase(context: Context): WordBestRecordDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordBestRecordDatabase::class.java,
                    "WorldBestRecord"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}