package com.yi.xxoo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yi.xxoo.page.levelPage.LevelPage

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    startDestination: String = Screen.LoginPage.route,
    paddingValues: PaddingValues
){
    NavHost(navController = navHostController, startDestination = startDestination){
        composable(Screen.LevelPage.route){
            LevelPage()
        }
    }
}