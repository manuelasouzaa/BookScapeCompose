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
fun SearchScreen(
    viewModel: SharedViewModel,
    navController: NavController,
) {
    BackHandler {
        navController.navigateUp()
    }

    viewModel.cleanTextField()
    viewModel.cleanApiAnswer()

    val list = runBlocking { viewModel.bookList.first() }

    BookScapeList(
        title = "Found books",
        list = list,
        onClick = {
            viewModel.sendBook(it)
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