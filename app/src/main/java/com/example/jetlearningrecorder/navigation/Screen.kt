package com.example.jetlearningrecorder.navigation

sealed class Screen(
    val route: String
){
    object MainScreen: Screen(ROUTE_MAIN_SCREEN)
    object ListScreen: Screen (ROUTE_LIST_SCREEN)

    private companion object{
        const val ROUTE_MAIN_SCREEN = "main_screen"
        const val ROUTE_LIST_SCREEN = "list_screen"
    }

}
