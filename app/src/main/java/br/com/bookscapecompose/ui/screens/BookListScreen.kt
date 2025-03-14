package br.com.bookscapecompose.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.bookscapecompose.ui.components.BookScapeList
import br.com.bookscapecompose.ui.viewmodels.SharedViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun BookListScreen(
    viewModel: SharedViewModel,
    navController: NavController,
) {
    BackHandler {
        navController.navigateUp()
    }

    val context = LocalContext.current
    val loading = viewModel.loading.collectAsState()
    val books = viewModel.bookList.collectAsState()
    viewModel.showBooks(context)

    if (loading.value)
        Column (Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally){
            CircularProgressIndicator(modifier = Modifier)
        }


    if (!loading.value)
        BookScapeList(
            returnClick = { navController.navigateUp() },
            title = "My BookList",
            list = books.value,
            onClick = {
                runBlocking { viewModel.sendBook(it) }
                navController.navigate("SavedBookDetailsScreen")
            }
        )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookListScreenPreview() {
    BookListScreen(SharedViewModel(), rememberNavController())
}