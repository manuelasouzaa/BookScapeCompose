package br.com.bookscapecompose.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.bookscapecompose.ui.theme.BookScapeComposeTheme

@Composable
fun BookScapeAlertDialog(
    modifier: Modifier = Modifier,
    buttonModifier: Modifier,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    confirmButtonText: String,
    onDismissClick: () -> Unit,
    dismissButtonText: String,
    title: String,
    text: String?,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            BookScapeButton(
                onClick = onConfirmClick,
                modifier = buttonModifier,
                buttonText = confirmButtonText
            )
        },
        dismissButton = {
            BookScapeButton(
                onClick = onDismissClick,
                modifier = buttonModifier,
                buttonText = dismissButtonText
            )
        },
        modifier = modifier,
        title = {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp),
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            text?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        },
        shape = RoundedCornerShape(15),
        containerColor = MaterialTheme.colorScheme.background,
        textContentColor = MaterialTheme.colorScheme.onTertiary,
        tonalElevation = 15.dp
    )
}

@Preview(showBackground = true)
@Composable
private fun BookScapeAlertDialogPreview() {
    BookScapeComposeTheme {
        BookScapeAlertDialog(
            buttonModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            modifier = Modifier.padding(20.dp),
            onDismissRequest = {},
            onConfirmClick = {},
            confirmButtonText = "Confirm button",
            onDismissClick = {},
            dismissButtonText = "Dismiss button",
            title = "Alert Dialog",
            text = "Alert Dialog preview text"
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BookScapeAlertDialogDarkThemePreview() {
    BookScapeComposeTheme {
        BookScapeAlertDialog(
            buttonModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            modifier = Modifier.padding(20.dp),
            onDismissRequest = {},
            onConfirmClick = {},
            confirmButtonText = "Confirm button",
            onDismissClick = {},
            dismissButtonText = "Dismiss button",
            title = "Alert Dialog",
            text = "Alert Dialog preview text"
        )
    }
}