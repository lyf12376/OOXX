package com.yi.xxoo.navigation

sealed class Screen(val route:String, val description:String){
    object LoginPage : Screen("LoginPage", "登录")

    object LevelPage : Screen("LevelPage", "关卡")

    object OfflineGamePage : Screen("OfflineGamePage/{level}", "单机游戏")

    object MinePage : Screen("MinePage", "我的")

    object RegisterPage : Screen("RegisterPage", "注册")

    object DocumentPage : Screen("DocumentPage/{account}", "资料")

    object AchievementPage : Screen("AchievementPage","成就")

    object StatisticPage : Screen("StatisticPage","数据")

    object RankPage:Screen("RankPage","排名")

    object MatchPage:Screen("MatchPage","匹配")

    object PreparePage:Screen("PreParePage","准备")

    object OnlineGamePage:Screen("OnlineGamePage","联机游戏")
}