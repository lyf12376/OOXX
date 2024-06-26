package com.yi.xxoo.bean.login

import com.yi.xxoo.Room.user.User

data class LoginResponse(
    val code:Int,
    val message:String,
    val data:User?
)
