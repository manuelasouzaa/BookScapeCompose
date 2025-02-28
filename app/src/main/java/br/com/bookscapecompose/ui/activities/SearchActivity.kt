package br.com.bookscapecompose.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.ui.screens.SearchScreen
import br.com.bookscapecompose.ui.theme.BookScapeComposeTheme
import br.com.bookscapecompose.ui.viewmodels.MainActivityViewModel

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val bookList = intent.getSerializableExtra("bookList") as List<Book?>
        setContent {
            BookScapeComposeTheme {
                Scaffold {
                    Search(Modifier.padding(it), list = bookList)
                }
            }
        }
    }
}

@Composable
fun Search(modifier: Modifier = Modifier, list: List<Book?>) {
    SearchScreen(viewModel = MainActivityViewModel(), modifier = modifier, list = list)
}

@Preview
@Composable
fun SearchPreview() {
    App()
}