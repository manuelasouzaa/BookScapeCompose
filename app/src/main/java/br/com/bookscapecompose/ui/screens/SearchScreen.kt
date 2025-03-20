package br.com.bookscapecompose.ui.screens

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import br.com.bookscapecompose.sampledata.sampleList
import br.com.bookscapecompose.ui.components.BookScapeList
import br.com.bookscapecompose.ui.theme.BookScapeComposeTheme
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

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SearchScreenLightThemePreview() {
    BookScapeComposeTheme {
        BookScapeList(
            returnClick = {},
            title = "Found books",
            list = sampleList,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SearchScreenDarkThemePreview() {
    BookScapeComposeTheme {
        BookScapeList(
            returnClick = {},
            title = "Found books",
            list = sampleList,
            onClick = {}
        )
    }
}