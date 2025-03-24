package br.com.bookscapecompose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import br.com.bookscapecompose.ui.screens.AccountScreen
import br.com.bookscapecompose.ui.screens.BookDetailsScreen
import br.com.bookscapecompose.ui.screens.BookListScreen
import br.com.bookscapecompose.ui.screens.MainScreen
import br.com.bookscapecompose.ui.screens.SavedBookDetailsScreen
import br.com.bookscapecompose.ui.screens.SearchScreen
import br.com.bookscapecompose.ui.screens.SignInScreen
import br.com.bookscapecompose.ui.screens.SignUpScreen
import br.com.bookscapecompose.ui.screens.SplashScreen
import br.com.bookscapecompose.ui.viewmodels.AccountViewModel
import br.com.bookscapecompose.ui.viewmodels.BookDetailsViewModel
import br.com.bookscapecompose.ui.viewmodels.BookListViewModel
import br.com.bookscapecompose.ui.viewmodels.MainViewModel
import br.com.bookscapecompose.ui.viewmodels.AppState
import br.com.bookscapecompose.ui.viewmodels.RootViewModel
import br.com.bookscapecompose.ui.viewmodels.SavedBookDetailsViewModel
import br.com.bookscapecompose.ui.viewmodels.SearchViewModel
import br.com.bookscapecompose.ui.viewmodels.SignInViewModel
import br.com.bookscapecompose.ui.viewmodels.SignUpViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookScapeNavHost(navController: NavHostController) {

    val rootViewModel = koinViewModel<RootViewModel>()
    LaunchedEffect(rootViewModel) {
        rootViewModel.preferencesState()
    }

    val signUpViewModel = koinViewModel<SignUpViewModel>()
    val signInViewModel = koinViewModel<SignInViewModel>()
    val mainViewModel = koinViewModel<MainViewModel>()
    val accountViewModel = koinViewModel<AccountViewModel>()
    val bookListViewModel = koinViewModel<BookListViewModel>()
    val searchViewModel = koinViewModel<SearchViewModel>()
    val bookDetailsViewModel = koinViewModel<BookDetailsViewModel>()
    val savedBookDetailsViewModel = koinViewModel<SavedBookDetailsViewModel>()

    val loadedPreferences by rootViewModel.appState.collectAsState()

    val startDestination = when (loadedPreferences) {
        AppState.Logged ->
            "MainScreen"

        AppState.NotLogged ->
            "SignInScreen"

        else ->
            "SplashScreen"
    }

    NavHost(navController = navController, startDestination = startDestination) {

        composable("SignUpScreen") {
            SignUpScreen(signUpViewModel, navController)
        }
        composable("SignInScreen") {
            SignInScreen(signInViewModel, navController)
        }
        composable("MainScreen") {
            MainScreen(mainViewModel, navController)
        }
        composable("SearchScreen") {
            SearchScreen(searchViewModel, navController)
        }
        composable("BookDetailsScreen") {
            BookDetailsScreen(bookDetailsViewModel, navController)
        }
        composable("SavedBookDetailsScreen") {
            SavedBookDetailsScreen(savedBookDetailsViewModel, navController)
        }
        composable("AccountScreen") {
            AccountScreen(accountViewModel, navController)
        }
        composable("BookListScreen") {
            BookListScreen(bookListViewModel, navController)
        }
        composable("SplashScreen") {
            SplashScreen()
        }

    }

    LaunchedEffect(loadedPreferences) {
        when (loadedPreferences) {
            AppState.NotLogged -> {
                navController.navigate("SignInScreen", navOptions {
                    popUpTo("SplashScreen") { inclusive = true }
                    launchSingleTop = true
                })
            }

            AppState.Logged -> {
                navController.navigate("MainScreen", navOptions {
                    popUpTo("SplashScreen") { inclusive = true }
                    launchSingleTop = true
                })
            }

            else -> {}
        }
    }
}