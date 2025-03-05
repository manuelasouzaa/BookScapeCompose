package br.com.bookscapecompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.bookscapecompose.ui.screens.BookDetailsScreen
import br.com.bookscapecompose.ui.screens.MainScreen
import br.com.bookscapecompose.ui.screens.SearchScreen
import br.com.bookscapecompose.ui.viewmodels.SharedViewModel

@Composable
fun BookScapeNavHost(navController: NavHostController) {
    val sharedViewModel = SharedViewModel()

    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            MainScreen(sharedViewModel, navController)
        }
        composable("SearchScreen") {
            SearchScreen(sharedViewModel, navController)
        }
        composable("BookDetailsScreen") {
            BookDetailsScreen(sharedViewModel, navController)
        }
    }
}