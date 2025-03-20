package br.com.bookscapecompose.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.bookscapecompose.ui.theme.BookScapeComposeTheme

@Composable
fun BookScapeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonText: String,
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
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(5.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun BookScapeButtonLightThemePreview() {
    BookScapeComposeTheme {
        BookScapeButton(onClick = {}, buttonText = "Button text")
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BookScapeButtonPreview() {
    BookScapeComposeTheme {
        BookScapeButton(onClick = {}, buttonText = "Button text")
    }
}