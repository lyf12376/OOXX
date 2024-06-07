package com.yi.xxoo.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LoginTime::class], version =1, exportSchema = false)
abstract class TimeDataBase : RoomDatabase(){
    abstract fun timeDao(): TimeDao

    companion object{
        @Volatile
        private var INSTANCE: TimeDataBase? = null

        fun getDatabase(context: Context): TimeDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TimeDataBase::class.java,
                    "Time"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}