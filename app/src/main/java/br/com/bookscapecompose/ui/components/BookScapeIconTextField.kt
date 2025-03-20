package br.com.bookscapecompose.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import br.com.bookscapecompose.ui.theme.BookScapeComposeTheme

@Composable
fun BookScapeIconTextField(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchChange: (String) -> Unit,
    onClick: () -> Unit,
) {
    TextField(
        value = searchText,
        onValueChange = {
            onSearchChange(it)
        },
        label = {
            Text(
                text = "Search a book or an author",
                style = MaterialTheme.typography.labelSmall
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.clickable {
                    onClick()
                }
            )
        },
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
            focusedLabelColor = MaterialTheme.colorScheme.onBackground,
            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
            focusedTextColor = MaterialTheme.colorScheme.onTertiary,
            unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
            cursorColor = MaterialTheme.colorScheme.onPrimary,
        ),
        textStyle = MaterialTheme.typography.bodySmall,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    )
}

@Preview(showBackground = true)
@Composable
private fun BookScapeIconTextFieldLightThemePreview() {
    BookScapeComposeTheme {
        BookScapeIconTextField(
            searchText = "",
            onSearchChange = {},
            onClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BookScapeIconTextFieldDarkThemePreview() {
    BookScapeComposeTheme {
        BookScapeIconTextField(
            searchText = "",
            onSearchChange = {},
            onClick = {}
        )
    }
}