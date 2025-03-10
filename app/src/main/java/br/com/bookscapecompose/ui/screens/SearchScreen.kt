package br.com.bookscapecompose.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.ui.components.BookScapeList
import br.com.bookscapecompose.ui.viewmodels.SharedViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@Composable
fun SearchScreen(
    viewModel: SharedViewModel,
    navController: NavController,
) {
    val list: List<Book?> = runBlocking { viewModel.bookList.first() }
    viewModel.cleanTextField()

    BackHandler {
        navController.navigateUp()
    }

    BookScapeList(
        title = "Found books",
        list = list,
        onClick = {
            runBlocking {
                viewModel.sendBook(it)
            }
            navController.navigate("BookDetailsScreen")
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        SharedViewModel(), rememberNavController()
    )
}