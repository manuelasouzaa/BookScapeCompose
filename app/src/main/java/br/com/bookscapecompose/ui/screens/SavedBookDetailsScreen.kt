package br.com.bookscapecompose.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavController
import br.com.bookscapecompose.R
import br.com.bookscapecompose.ui.viewmodels.BookMessage
import br.com.bookscapecompose.ui.viewmodels.SharedViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun SavedBookDetailsScreen(
    viewModel: SharedViewModel,
    navController: NavController,
) {
    val context = LocalContext.current

    val uriHandler = LocalUriHandler.current

    val bookMessage = viewModel.bookMessage.collectAsState()

    val bookmarkIcon =
        if (bookMessage.value == BookMessage.AddedBook)
            R.drawable.ic_remove
        else
            R.drawable.ic_add

    BackHandler {
        navController.navigateUp()
    }

    val answer = runBlocking { viewModel.verifyClickedBook(context) }
    if (answer == null)
        navController.navigate("MainScreen")
    if (answer != null) {
        BookDetails(
            returnClick = { navController.navigateUp() },
            bookImageUrl = answer.image ?: "",
            bookmarkIcon = bookmarkIcon,
            bookmarkIconClick = {
                //TODO: dialog asking if the user wishes to remove the book from the list
            },
            bookTitle = answer.title,
            bookAuthors = answer.authors ?: "",
            bookDesc = answer.description ?: "",
            purchaseButtonClick = {
                uriHandler.openUri(answer.link)
            }
        )
    }
}