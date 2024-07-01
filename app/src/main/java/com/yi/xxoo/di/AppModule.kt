package com.yi.xxoo.di

import android.content.Context
import com.coder.vincent.sharp_retrofit.call_adapter.flow.FlowCallAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
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
import com.yi.xxoo.network.gameHistory.GameHistoryService
import com.yi.xxoo.network.gameTime.GameTimeService
import com.yi.xxoo.network.match.MatchService
import com.yi.xxoo.network.onlineGameHistory.OnlineGameHistoryService
import com.yi.xxoo.network.passNumRank.PassNumRankService
import com.yi.xxoo.network.user.UserService
import com.yi.xxoo.network.worldBest.WorldBestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val LocalUrl = "http://106.52.31.86:80"
//    private const val LocalUrl = "http://10.33.107.244:8080"
//    private const val LocalUrl = "http://10.70.143.129:8080"
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    @Singleton
    @Provides
    fun provideClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(3000L, TimeUnit.MILLISECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(interceptor = loggingInterceptor)
            .build()
    }

    /**
     * 在目录中使用Hilt提供多个相对类型的实例对象，通过"@Named"注解进行区分
     * 也可以通过"@Qualifier"限定符定义新的注解进行区分*/

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(LocalUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideGameHistoryService(retrofit: Retrofit): GameHistoryService {
        return retrofit.create(GameHistoryService::class.java)
    }

    @Singleton
    @Provides
    fun provideMatchService(retrofit: Retrofit): MatchService{
        return retrofit.create(MatchService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideGameTimeService(retrofit: Retrofit):GameTimeService{
        return retrofit.create(GameTimeService::class.java)
    }

    @Singleton
    @Provides
    fun providePassNumRankService(retrofit: Retrofit): PassNumRankService {
        return retrofit.create(PassNumRankService::class.java)
    }

    @Singleton
    @Provides
    fun provideWorldBestService(retrofit: Retrofit):WorldBestService{
        return retrofit.create(WorldBestService::class.java)
    }

    @Singleton
    @Provides
    fun provideOnlineGameService(retrofit: Retrofit): OnlineGameHistoryService {
        return retrofit.create(OnlineGameHistoryService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext context : Context): UserDatabase {
        return UserDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideUserDao(database: UserDatabase): UserDao {
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


//    private var socket = Socket()
//    private var out: PrintWriter? = null
//    private var `in`: BufferedReader? = null
//
//    fun connect() {
//        Log.d("TAG", "connect: ddddddddddddddddddddd")
//        try {
//            socket = Socket("10.70.143.129", 6666).also { sock ->
//                out = PrintWriter(sock.getOutputStream(), true)
//                `in` = BufferedReader(InputStreamReader(sock.getInputStream()))
//                out!!.println(UserData.account)
//            }
//        }catch (e:Exception){
//            Log.d("TAG", "connect: ddddddddddddd ${e.message}")
//        }
//
//    }
//
//    fun disConnectSocket() {
//        try {
//            socket.close()
//            out?.close()
//            `in`?.close()
//        } catch (e: Exception) {
//            println("Error closing resources: ${e.message}")
//        }
//    }
//
//    @Singleton
//    @Provides
//    fun provideSocket(): Socket
//    {
//        return socket
//    }
//
//    @Singleton
//    @Provides
//    fun provideOut(): PrintWriter?
//    {
//        return out
//    }
//
//    @Singleton
//    @Provides
//    fun provideIn(): BufferedReader?
//    {
//        return `in`
//    }



}