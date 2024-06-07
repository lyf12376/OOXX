package com.yi.xxoo.navigation

sealed class Screen(val route:String, val description:String){
    object LoginPage : Screen("LoginPage", "登录")

    object LevelPage : Screen("LevelPage", "关卡")

    object GamePage : Screen("GamePage", "游戏")

    object MinePage : Screen("MinePage", "我的")
}