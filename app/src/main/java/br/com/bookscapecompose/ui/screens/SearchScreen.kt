package br.com.bookscapecompose.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.bookscapecompose.R
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.ui.components.BookItem
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
        navController.navigate("MainScreen")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Found books",
            fontSize = 28.sp,
            fontFamily = FontFamily((listOf(Font(R.font.kavoon)))),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(list) { bookItem ->
                bookItem?.let { book ->
                    BookItem(book = book, onClick = {
                        runBlocking {
                            viewModel.sendBook(book)
                        }
                        navController.navigate("BookDetailsScreen")
                    })
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        SharedViewModel(), rememberNavController()
    )
}