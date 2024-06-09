package com.yi.xxoo.di

import android.content.Context
import com.yi.xxoo.Room.game.GameDao
import com.yi.xxoo.Room.game.GameDatabase
import com.yi.xxoo.Room.history.GameHistoryDao
import com.yi.xxoo.Room.history.GameHistoryDatabase
import com.yi.xxoo.Room.rank.passNum.PassNumRankDao
import com.yi.xxoo.Room.rank.passNum.PassNumRankDatabase
import com.yi.xxoo.Room.rank.time.GameTimeRankDao
import com.yi.xxoo.Room.rank.time.GameTimeRankDatabase
import com.yi.xxoo.Room.rank.worldBest.WorldBestRecordDao
import com.yi.xxoo.Room.rank.worldBest.WorldBestRecordDatabase
import com.yi.xxoo.Room.savedUser.SavedUserDao
import com.yi.xxoo.Room.savedUser.SavedUserDatabase
import com.yi.xxoo.Room.user.UserDao
import com.yi.xxoo.Room.user.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun getUserDatabase(@ApplicationContext context : Context): UserDatabase {
        return UserDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun getUserDao(database: UserDatabase): UserDao {
        return database.userDao()
    }

    @Singleton
    @Provides
    fun provideGameDatabase(@ApplicationContext context: Context): GameDatabase {
        return GameDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideGameDao(database: GameDatabase): GameDao {
        return database.gameDao()
    }

    @Singleton
    @Provides
    fun provideGameTimeRankDatabase(@ApplicationContext context: Context): GameTimeRankDatabase {
        return GameTimeRankDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideGameTimeRankDao(database: GameTimeRankDatabase): GameTimeRankDao {
        return database.gameTimeRankDao()
    }

    @Singleton
    @Provides
    fun providePassNumRankDatabase(@ApplicationContext context: Context): PassNumRankDatabase {
        return PassNumRankDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun providePassNumRankDao(database: PassNumRankDatabase): PassNumRankDao {
        return database.passNumRankDao()
    }

    @Singleton
    @Provides
    fun provideWorldBestRecordDatabase(@ApplicationContext context: Context): WorldBestRecordDatabase {
        return WorldBestRecordDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideWorldBestRecordDao(database: WorldBestRecordDatabase): WorldBestRecordDao {
        return database.wordBestRecordDao()
    }

    @Singleton
    @Provides
    fun provideGameHistoryDatabase(@ApplicationContext context: Context): GameHistoryDatabase {
        return GameHistoryDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideGameHistoryDao(database: GameHistoryDatabase): GameHistoryDao {
        return database.gameHistoryDao()
    }

    @Singleton
    @Provides
    fun provideSavedUserDatabase(@ApplicationContext context: Context): SavedUserDatabase {
        return SavedUserDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideSavedUserDao(database: SavedUserDatabase): SavedUserDao {
        return database.savedUserDao()
    }



}