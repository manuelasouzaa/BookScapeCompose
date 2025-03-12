package br.com.bookscapecompose.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookScapeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonText: String
    ) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        modifier = modifier,
    ) {
        Text(
            text = buttonText,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Preview
@Composable
private fun BookScapeButtonPreview() {
    BookScapeButton(onClick = {}, buttonText = "Button text")
}