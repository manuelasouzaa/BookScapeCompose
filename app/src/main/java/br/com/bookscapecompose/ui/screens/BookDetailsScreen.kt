package br.com.bookscapecompose.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.bookscapecompose.R
import br.com.bookscapecompose.expressions.toast
import br.com.bookscapecompose.ui.components.BookDetails
import br.com.bookscapecompose.ui.components.BookScapeAlertDialog
import br.com.bookscapecompose.ui.viewmodels.BookMessage
import br.com.bookscapecompose.ui.viewmodels.SharedViewModel

@Composable
fun BookDetailsScreen(
    viewModel: SharedViewModel,
    navController: NavController,
) {
    BackHandler {
        navController.navigateUp()
    }

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val bookMessage = viewModel.bookMessage.collectAsState()
    val openDialog = remember { mutableStateOf(false) }

    val icon =
        if (bookMessage.value == BookMessage.AddedBook)
            R.drawable.ic_added
        else
            R.drawable.ic_add

    val answer = viewModel.verifyClickedBookValue(context)

    answer?.let {
        BookDetails(
            returnClick = { navController.navigateUp() },
            bookImageUrl = answer.image ?: "",
            bookmarkIcon = icon,
            bookmarkIconClick = {
                if (icon == R.drawable.ic_add) {
                    viewModel.saveBook(context)
                    openDialog.value = true
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
    } ?: navController.navigate("MainScreen")

    if (openDialog.value) {
        BookScapeAlertDialog(
            buttonModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            onDismissClick = { openDialog.value = false },
            onConfirmClick = {
                navController.navigate("BookListScreen")
                openDialog.value = false
            },
            confirmButtonText = "Go to BookList",
            onDismissRequest = { openDialog.value = false },
            dismissButtonText = "Close",
            title = "Book saved successfully!",
            text = null
        )
    }
}