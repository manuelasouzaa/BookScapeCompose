package br.com.bookscapecompose.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import br.com.bookscapecompose.ui.theme.BookScapeComposeTheme

@Composable
fun BookScapeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType,
    isPassword: Boolean,
) {
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall
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
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun BookScapeEmptyTextFieldLightModePreview() {
    BookScapeComposeTheme {
        BookScapeTextField(
            value = "",
            onValueChange = {},
            label = "Test",
            keyboardType = KeyboardType.Text,
            isPassword = true
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun BookScapeFilledTextFieldLightModePreview() {
    BookScapeComposeTheme {
        BookScapeTextField(
            value = "test",
            onValueChange = {},
            label = "Test",
            keyboardType = KeyboardType.Text,
            isPassword = false
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun BookScapePasswordTextFieldLightModePreview() {
    BookScapeComposeTheme {
        BookScapeTextField(
            value = "test",
            onValueChange = {},
            label = "Test",
            keyboardType = KeyboardType.Text,
            isPassword = true
        )
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BookScapeEmptyTextFieldDarkModePreview() {
    BookScapeComposeTheme {
        BookScapeTextField(
            value = "",
            onValueChange = {},
            label = "Test",
            keyboardType = KeyboardType.Text,
            isPassword = true
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BookScapeFilledTextFieldDarkModePreview() {
    BookScapeComposeTheme {
        BookScapeTextField(
            value = "test",
            onValueChange = {},
            label = "Test",
            keyboardType = KeyboardType.Text,
            isPassword = false
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BookScapePasswordTextFieldDarkModePreview() {
    BookScapeComposeTheme {
        BookScapeTextField(
            value = "test",
            onValueChange = {},
            label = "Test",
            keyboardType = KeyboardType.Text,
            isPassword = true
        )
    }
}