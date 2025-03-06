package br.com.bookscapecompose.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

@Composable
fun BookScapeTextField(
    value: String,
    onValueChange: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType,
) {
    TextField(
        value = value,
        onValueChange = {
            onValueChange()
        },
        label = {
            Text(
                text = label,
                fontSize = 16.sp
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
        textStyle = TextStyle(
            fontSize = 20.sp
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
    )
}