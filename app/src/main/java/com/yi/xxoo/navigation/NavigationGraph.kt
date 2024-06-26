package com.yi.xxoo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yi.xxoo.page.achievementPage.AchievementPage
import com.yi.xxoo.page.documentPage.DocumentPage
import com.yi.xxoo.page.forgetPage.ForgetPage
import com.yi.xxoo.page.gameHistoryPage.GameHistoryPage
import com.yi.xxoo.page.offlineGamePage.OfflineGamePage
import com.yi.xxoo.page.levelPage.LevelPage
import com.yi.xxoo.page.loginPage.LoginPage
import com.yi.xxoo.page.matchPage.MatchPage
import com.yi.xxoo.page.minePage.MinePage
import com.yi.xxoo.page.onlineGamePage.OnlineGamePage
import com.yi.xxoo.page.preparePage.PreparePage
import com.yi.xxoo.page.rankPage.RankPage
import com.yi.xxoo.page.registerPage.RegisterPage
import com.yi.xxoo.page.rulePgae.RulePage
import com.yi.xxoo.page.settlePage.SettlePage
import com.yi.xxoo.page.statisticPage.StatisticPage

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    startDestination: String = Screen.LoginPage.route,
){

    NavHost(navController = navHostController, startDestination = startDestination){
        composable(Screen.LevelPage.route){
            LevelPage(navHostController)
        }
        composable(Screen.MinePage.route){
            MinePage(navHostController)
        }
        composable(
            Screen.OfflineGamePage.route,
            arguments = listOf(navArgument("level"){type = NavType.IntType})
        ){
            OfflineGamePage(navHostController,level = it.arguments?.getInt("level") ?: 0)
        }
        composable(Screen.LoginPage.route){
            LoginPage(navHostController)
        }
        composable(Screen.RegisterPage.route){
            RegisterPage(navController = navHostController)
        }
        composable(
            route = Screen.DocumentPage.route,
        ){
            DocumentPage(navController = navHostController)
        }
        composable(Screen.AchievementPage.route){
            AchievementPage(navHostController)
        }
        composable(Screen.StatisticPage.route){
            StatisticPage(navHostController)
        }
        composable(Screen.RankPage.route){
            RankPage(navController = navHostController)
        }
        composable(Screen.MatchPage.route){
            MatchPage(navHostController)
        }
        composable(Screen.PreparePage.route){
            PreparePage(navHostController)
        }
        composable(Screen.OnlineGamePage.route){
            OnlineGamePage(navHostController)
        }
        composable(Screen.SettlePage.route){
            SettlePage(navHostController)
        }
        composable(Screen.GameHistoryPage.route){
            GameHistoryPage(navController = navHostController)
        }
        composable(Screen.ForgetPage.route){
            ForgetPage(navController = navHostController)
        }
        composable(Screen.RulePage.route){
            RulePage(navController = navHostController)
        }
    }
}