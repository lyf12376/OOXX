package com.yi.xxoo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yi.xxoo.page.gamePage.GameGrid
import com.yi.xxoo.page.gamePage.GamePage
import com.yi.xxoo.page.levelPage.LevelPage
import com.yi.xxoo.page.loginPage.LoginPage
import com.yi.xxoo.page.minePage.MinePage

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    startDestination: String = Screen.LoginPage.route,
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
    }
}