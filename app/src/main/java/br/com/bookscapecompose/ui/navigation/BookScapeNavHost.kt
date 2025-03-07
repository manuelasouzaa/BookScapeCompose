package br.com.bookscapecompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.bookscapecompose.ui.screens.AccountScreen
import br.com.bookscapecompose.ui.screens.BookDetailsScreen
import br.com.bookscapecompose.ui.screens.BookListScreen
import br.com.bookscapecompose.ui.screens.MainScreen
import br.com.bookscapecompose.ui.screens.SearchScreen
import br.com.bookscapecompose.ui.screens.SignInScreen
import br.com.bookscapecompose.ui.screens.SignUpScreen
import br.com.bookscapecompose.ui.viewmodels.SharedViewModel
import br.com.bookscapecompose.ui.viewmodels.SignInViewModel
import br.com.bookscapecompose.ui.viewmodels.SignUpViewModel

@Composable
fun BookScapeNavHost(navController: NavHostController) {
    val signUpViewModel = SignUpViewModel()
    val signInViewModel = SignInViewModel()
    val sharedViewModel = SharedViewModel()

    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("SignUpScreen") {
            SignUpScreen(signUpViewModel, navController)
        }
        composable("SignInScreen") {
            SignInScreen(signInViewModel, navController)
        }
        composable("MainScreen") {
            MainScreen(sharedViewModel, navController)
        }
        composable("SearchScreen") {
            SearchScreen(sharedViewModel, navController)
        }
        composable("BookDetailsScreen") {
            BookDetailsScreen(sharedViewModel, navController)
        }
        composable("AccountScreen") {
            AccountScreen(navController)
        }
        composable("BookListScreen") {
            BookListScreen(sharedViewModel, navController)
        }
    }
}