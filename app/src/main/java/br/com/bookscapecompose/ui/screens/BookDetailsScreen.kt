package br.com.bookscapecompose.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.bookscapecompose.R
import br.com.bookscapecompose.expressions.toast
import br.com.bookscapecompose.model.Book
import br.com.bookscapecompose.sampledata.sampleBook
import br.com.bookscapecompose.ui.components.BookDetails
import br.com.bookscapecompose.ui.components.BookScapeAlertDialog
import br.com.bookscapecompose.ui.viewmodels.BookDetailsViewModel
import br.com.bookscapecompose.ui.viewmodels.BookMessage

@Composable
fun BookDetailsScreen(viewModel: BookDetailsViewModel, navController: NavController) {

    BackHandler {
        navController.navigateUp()
        viewModel.clearClickedBook()
    }

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val bookMessage by viewModel.bookMessage.collectAsState()
    val openDialog = remember { mutableStateOf(false) }

    val icon =
        if (bookMessage == BookMessage.AddedBook)
            R.drawable.ic_added
        else
            R.drawable.ic_add

    val answer = viewModel.verifyClickedBookValue()

    BookDetailsScreenContent(
        answer = answer,
        icon = icon,
        returnClick = { navController.navigateUp() },
        saveBook = { viewModel.saveBook() },
        openDialog = openDialog,
        uriHandler = uriHandler,
        navigate = { navController.navigate(it) },
        toastMessage = { toast(context, it) }
    )
}

@Composable
fun BookDetailsScreenContent(
    answer: Book?,
    icon: Int,
    returnClick: () -> Unit,
    saveBook: () -> Unit,
    openDialog: MutableState<Boolean>,
    uriHandler: UriHandler,
    navigate: (String) -> Unit,
    toastMessage: (String) -> Unit,
) {
    answer?.let {
        BookDetails(
            returnClick = { returnClick() },
            bookImageUrl = answer.image ?: "",
            bookmarkIcon = icon,
            bookmarkIconClick = {
                if (icon == R.drawable.ic_add) {
                    saveBook()
                    openDialog.value = true
                }
                if (icon == R.drawable.ic_added)
                    toastMessage("This book is already on your list!")
            },
            bookTitle = answer.title,
            bookAuthors = answer.authors ?: "",
            bookDesc = answer.description ?: "",
            purchaseButtonClick = { uriHandler.openUri(answer.link) }
        )
    } ?: navigate("MainScreen")

    if (openDialog.value) {
        BookScapeAlertDialog(
            buttonModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            onDismissClick = { openDialog.value = false },
            onConfirmClick = {
                navigate("BookListScreen")
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookDetailsScreenPreview() {
    BookDetailsScreenContent(
        answer = sampleBook,
        icon = R.drawable.ic_add,
        returnClick = {},
        saveBook = {},
        openDialog = remember { mutableStateOf(false) },
        uriHandler = LocalUriHandler.current,
        navigate = {},
        toastMessage = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookDetailsScreenOpenDialogPreview() {
    BookDetailsScreenContent(
        answer = sampleBook,
        icon = R.drawable.ic_added,
        returnClick = {},
        saveBook = {},
        openDialog = remember { mutableStateOf(true) },
        uriHandler = LocalUriHandler.current,
        navigate = {},
        toastMessage = {}
    )
}