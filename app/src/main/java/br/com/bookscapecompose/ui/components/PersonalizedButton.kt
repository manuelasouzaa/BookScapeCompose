package br.com.bookscapecompose.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun PersonalizedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    imageVector: ImageVector
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        modifier = modifier,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = text,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(5.dp)
        )
    }
}