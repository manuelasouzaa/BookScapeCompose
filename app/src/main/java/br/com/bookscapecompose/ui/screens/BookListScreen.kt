package br.com.bookscapecompose.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.bookscapecompose.ui.components.BookScapeList
import br.com.bookscapecompose.ui.viewmodels.SharedViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@Composable
fun BookListScreen(
    viewModel: SharedViewModel,
    navController: NavController,
) {
    BackHandler {
        navController.navigateUp()
    }

    val books = viewModel.bookList.collectAsState()

    BookScapeList(
        returnClick = {
            navController.navigateUp()
        },
        title = "My BookList",
        list = books.value,
        onClick = {
            runBlocking {
                viewModel.sendBook(it)
            }
            navController.navigate("SavedBookDetailsScreen")
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookListScreenPreview() {
    BookListScreen(SharedViewModel(), rememberNavController())
}