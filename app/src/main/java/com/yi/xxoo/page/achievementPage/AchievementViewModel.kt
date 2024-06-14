package com.yi.xxoo.page.achievementPage

import androidx.lifecycle.ViewModel
import com.yi.xxoo.Room.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(private val userDao: UserDao): ViewModel(){

}