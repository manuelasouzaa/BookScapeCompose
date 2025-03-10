package br.com.bookscapecompose.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavController
import br.com.bookscapecompose.R
import br.com.bookscapecompose.expressions.toast
import br.com.bookscapecompose.ui.components.BookDetails
import br.com.bookscapecompose.ui.viewmodels.BookMessage
import br.com.bookscapecompose.ui.viewmodels.SharedViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun BookDetailsScreen(
    viewModel: SharedViewModel,
    navController: NavController,
) {
    val context = LocalContext.current

    val uriHandler = LocalUriHandler.current

    val bookMessage = viewModel.bookMessage.collectAsState()

    val icon =
        if (bookMessage.value == BookMessage.AddedBook)
            R.drawable.ic_added
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
            bookmarkIcon = icon,
            bookmarkIconClick = {
                if (icon == R.drawable.ic_add) {
                    runBlocking { viewModel.saveBook(context) }
                    //TODO: dialog saying that the book was added successfully and with a button that goes to the bookList
                }
                if (icon == R.drawable.ic_added)
                    toast(context, "This book is already on your list!")
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