package br.com.bookscapecompose.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.sampledata.sampleList
import br.com.bookscapecompose.ui.components.BookScapeList
import br.com.bookscapecompose.ui.viewmodels.BookListViewModel

@Composable
fun BookListScreen(bookListViewModel: BookListViewModel, navController: NavController) {
    BackHandler { navController.navigateUp() }

    val loading by bookListViewModel.loading.collectAsState()
    val books by bookListViewModel.bookList.collectAsState()

    bookListViewModel.showBooks()

    BookListScreenContent(
        loading = loading,
        returnClick = { navController.navigateUp() },
        books = books,
        onItemClick = { book ->
            book?.let {
                bookListViewModel.sendBook(it)
                navController.navigate("SavedBookDetailsScreen")
            }
        }
    )
}

@Composable
fun BookListScreenContent(
    loading: Boolean,
    returnClick: () -> Boolean,
    books: List<Book?>,
    onItemClick: (Book?) -> Unit,
) {
    if (loading)
        Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
            CircularProgressIndicator(modifier = Modifier)
        }

    if (!loading)
        BookScapeList(
            returnClick = { returnClick() },
            title = "My BookList",
            list = books,
            onClick = {
                onItemClick(it)
            }
        )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookListLoadedScreenPreview() {
    BookListScreenContent(
        loading = false,
        returnClick = { true },
        books = sampleList,
        onItemClick = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookListLoadingScreenPreview() {
    BookListScreenContent(
        loading = true,
        returnClick = { true },
        books = sampleList,
        onItemClick = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookListEmptyListScreenPreview() {
    BookListScreenContent(
        loading = false,
        returnClick = { true },
        books = emptyList(),
        onItemClick = {}
    )
}