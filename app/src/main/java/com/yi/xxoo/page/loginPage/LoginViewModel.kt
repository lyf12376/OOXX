package com.yi.xxoo.page.loginPage

import androidx.lifecycle.ViewModel
import com.yi.xxoo.Room.savedUser.SavedUser
import com.yi.xxoo.Room.savedUser.SavedUserDao
import com.yi.xxoo.Room.user.User
import com.yi.xxoo.Room.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val savedUserDao: SavedUserDao,private val userDao: UserDao): ViewModel(){

    val savedUserList: Flow<List<SavedUser>> = getSavedUser()

    suspend fun isLogin(account: String, password: String): User? {
        return userDao.login(account, password)
    }

    suspend fun saveUser(savedUser: SavedUser) {
        savedUserDao.saveUsers(savedUser)
    }

    private fun getSavedUser(): Flow<List<SavedUser>> {
        return savedUserDao.getSavedUsers()
    }

    suspend fun deleteUser(account: String) {
        savedUserDao.unRemember(account)
    }
}