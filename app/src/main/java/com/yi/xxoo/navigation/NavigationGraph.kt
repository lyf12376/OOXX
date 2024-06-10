package com.yi.xxoo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yi.xxoo.page.documentPage.BasicMessage
import com.yi.xxoo.page.gamePage.GameGrid
import com.yi.xxoo.page.gamePage.GamePage
import com.yi.xxoo.page.levelPage.LevelPage
import com.yi.xxoo.page.loginPage.LoginPage
import com.yi.xxoo.page.minePage.MinePage
import com.yi.xxoo.page.registerPage.RegisterPage

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    startDestination: String = Screen.LevelPage.route,
){
    NavHost(navController = navHostController, startDestination = startDestination){
        composable(Screen.LevelPage.route){
            LevelPage()
        }
        composable(Screen.MinePage.route){
            MinePage()
        }
        composable(Screen.GamePage.route){
            GamePage()
        }
        composable(Screen.LoginPage.route){
            LoginPage(navHostController)
        }
        composable(Screen.RegisterPage.route){
            RegisterPage(navController = navHostController)
        }
        composable(
            route = Screen.DocumentPage.route,
            arguments = listOf(navArgument("account"){type = NavType.StringType})
        ){
            BasicMessage(navController = navHostController, account = it.arguments?.getString("account") ?: "")
        }
    }
}