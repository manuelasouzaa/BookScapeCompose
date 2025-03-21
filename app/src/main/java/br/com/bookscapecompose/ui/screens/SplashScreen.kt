package br.com.bookscapecompose.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.bookscapecompose.R
import br.com.bookscapecompose.ui.theme.BookScapeComposeTheme

@Composable
fun SplashScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(R.drawable.logo_bookscape),
            modifier = Modifier
                .fillMaxSize(.35f),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenLightThemePreview() {
    BookScapeComposeTheme {
        SplashScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SplashScreenDarkThemePreview() {
    BookScapeComposeTheme {
        SplashScreen()
    }
}