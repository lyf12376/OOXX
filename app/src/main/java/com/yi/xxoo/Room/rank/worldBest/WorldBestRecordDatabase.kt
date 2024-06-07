package com.yi.xxoo.Room.rank.worldBest

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WorldBestRecord::class], version = 1)
abstract class WorldBestRecordDatabase : RoomDatabase(){
    abstract fun wordBestRecordDao(): WorldBestRecordDao

    companion object{
        @Volatile
        private var INSTANCE: WorldBestRecordDatabase? = null
        fun getDatabase(context: Context): WorldBestRecordDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorldBestRecordDatabase::class.java,
                    "WorldBestRecord"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}