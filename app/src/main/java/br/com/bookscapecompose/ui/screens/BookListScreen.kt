package br.com.bookscapecompose.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.bookscapecompose.model.Book
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
    val list = remember { mutableStateOf<List<Book?>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val books = viewModel.showBooks(context)
        list.value = books
        isLoading.value = false
    }

    if (isLoading.value) {
        CircularProgressIndicator()
    } else {
        BookScapeList(
            title = "My BookList",
            list = list.value,
            onClick = {
                runBlocking {
                    viewModel.sendBook(it)
                }
                navController.navigate("SavedBookDetailsScreen")
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookListScreenPreview() {
    BookListScreen(SharedViewModel(), rememberNavController())
}