package br.com.bookscapecompose.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavController
import br.com.bookscapecompose.R
import br.com.bookscapecompose.ui.components.BookDetails
import br.com.bookscapecompose.ui.viewmodels.BookMessage
import br.com.bookscapecompose.ui.viewmodels.SharedViewModel

@Composable
fun SavedBookDetailsScreen(
    viewModel: SharedViewModel,
    navController: NavController,
) {
    BackHandler {
        navController.navigateUp()
    }

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val bookMessage = viewModel.bookMessage.collectAsState()

    val bookmarkIcon =
        if (bookMessage.value == BookMessage.AddedBook)
            R.drawable.ic_remove
        else
            R.drawable.ic_add

    val answer = viewModel.verifyClickedBook(context)

    answer?.let {
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
    } ?: navController.navigate("MainScreen")
}