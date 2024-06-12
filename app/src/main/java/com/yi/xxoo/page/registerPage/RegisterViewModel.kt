package com.yi.xxoo.page.registerPage

import androidx.lifecycle.ViewModel
import com.yi.xxoo.Room.user.User
import com.yi.xxoo.Room.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userDao: UserDao) : ViewModel(){

    suspend fun register(account: String, password: String) {
        userDao.createUser(User("name",account,password,account,photo = ""))
    }

    suspend fun updatePersonalInformation(account: String, name: String, photo: String) {
        userDao.updateUserName(account,name)
        userDao.updateUserPhoto(account,photo)
    }



}