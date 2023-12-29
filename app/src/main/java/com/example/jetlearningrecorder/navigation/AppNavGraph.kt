package com.example.jetlearningrecorder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    listScreenContent: @Composable () -> Unit,

    ) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.MainScreen.route,
    ) {
        composable(Screen.MainScreen.route) {
            homeScreenContent()
        }

        composable(Screen.ListScreen.route) {
            listScreenContent()
        }
    }
}