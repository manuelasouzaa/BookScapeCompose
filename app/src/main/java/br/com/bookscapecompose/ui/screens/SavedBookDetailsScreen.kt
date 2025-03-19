package br.com.bookscapecompose.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import br.com.bookscapecompose.ui.viewmodels.SavedBookDetailsViewModel
import br.com.bookscapecompose.ui.viewmodels.SavedBookMessage
import kotlinx.coroutines.runBlocking

@Composable
fun SavedBookDetailsScreen(viewModel: SavedBookDetailsViewModel, navController: NavController) {

    BackHandler { navController.navigateUp() }

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val savedBookMessage by viewModel.savedBookMessage.collectAsState()
    val openDialog = remember { mutableStateOf(false) }

    val bookmarkIcon =
        if (savedBookMessage == SavedBookMessage.AddedBook || savedBookMessage == SavedBookMessage.Initial)
            R.drawable.ic_remove
        else
            R.drawable.ic_add

    val answer = viewModel.verifySavedClickedBookValue()

    LaunchedEffect(savedBookMessage) {
        when (savedBookMessage) {
            SavedBookMessage.DeletedBook ->
                toast(context, "Book removed successfully")

            SavedBookMessage.NotSavedBook ->
                toast(context, "Book removed successfully")

            SavedBookMessage.Error -> {
                toast(context, "An error occurred. Try again later.")
                navController.navigate("MainScreen")
            }

            SavedBookMessage.AddedBook ->
                toast(context, "Book added successfully!")

            else -> {}
        }
    }

    fun deleteBook() {
        viewModel.deleteBook()
    }

    fun addBookAgain() {
        runBlocking { viewModel.saveBook() }
    }

    answer?.let {
        BookDetails(
            returnClick = { navController.navigateUp() },
            bookImageUrl = answer.image ?: "",
            bookmarkIcon = bookmarkIcon,
            bookmarkIconClick = {
                if (bookmarkIcon == R.drawable.ic_remove)
                    openDialog.value = true
                if (bookmarkIcon == R.drawable.ic_add)
                    addBookAgain()
            },
            bookTitle = answer.title,
            bookAuthors = answer.authors ?: "",
            bookDesc = answer.description ?: "",
            purchaseButtonClick = { uriHandler.openUri(answer.link) }
        )
    } ?: navController.navigate("MainScreen")

    if (openDialog.value) {
        BookScapeAlertDialog(
            buttonModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp),
            onDismissRequest = { openDialog.value = false },
            onConfirmClick = {
                deleteBook()
                openDialog.value = false
            },
            confirmButtonText = "Remove",
            onDismissClick = { openDialog.value = false },
            dismissButtonText = "Cancel",
            title = "Confirmation",
            text = "Do you really wish to remove this book from your list? This action cannot be undone."
        )
    }

}