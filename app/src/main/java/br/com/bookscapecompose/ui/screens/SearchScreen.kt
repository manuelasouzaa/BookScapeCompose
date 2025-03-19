package br.com.bookscapecompose.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import br.com.bookscapecompose.sampledata.sampleList
import br.com.bookscapecompose.ui.components.BookScapeList
import br.com.bookscapecompose.ui.viewmodels.SearchViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel, navController: NavController) {

    fun returnToMainScreen() {
        viewModel.cleanApiAnswer()
        navController.navigateUp()
    }

    BackHandler { returnToMainScreen() }
    val list = viewModel.foundBooks.collectAsState()

    if (list.value.isNotEmpty()) {
        BookScapeList(
            returnClick = { returnToMainScreen() },
            title = "Found books",
            list = list.value,
            onClick = {
                viewModel.sendBook(it)
                navController.navigate("BookDetailsScreen")
            }
        )
    } else navController.navigateUp()

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SearchScreenPreview() {
    BookScapeList(
        returnClick = {},
        title = "Found books",
        list = sampleList,
        onClick = {}
    )
}