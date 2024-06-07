package com.yi.xxoo.di

import android.content.Context
import com.yi.xxoo.Room.game.GameDao
import com.yi.xxoo.Room.game.GameDatabase
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
}