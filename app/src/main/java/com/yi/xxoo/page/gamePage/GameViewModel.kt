package com.yi.xxoo.page.gamePage

import androidx.lifecycle.ViewModel
import com.yi.xxoo.Room.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val userDao: UserDao) : ViewModel(){


}