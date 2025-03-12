package br.com.bookscapecompose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookScapeAlertDialog(
    modifier: Modifier = Modifier.wrapContentSize(align = Alignment.Center),
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
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        },
        text = {
            text?.let {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
            }
        },
        shape = RoundedCornerShape(15),
        containerColor = MaterialTheme.colorScheme.background,
        textContentColor = MaterialTheme.colorScheme.onTertiary,
        tonalElevation = 15.dp
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BookScapeAlertDialogPreview() {
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