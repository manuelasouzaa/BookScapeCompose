package br.com.bookscapecompose.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    onPrimaryContainer = MediumBlueDarkTheme,
    onSecondaryContainer = LightBlueDarkTheme,
    background = PetroleumBlueDarkTheme,
    onBackground = LighterBlueLightTheme,
    primary = DarkBlueDarkTheme,
    onPrimary = LighterBlueLightTheme,
    secondary = LighterBlueDarkTheme,
    onSecondary = LighterBlueLightTheme,
    tertiary = MediumBlueDarkTheme,
    onTertiary = White,
    error = Red
)

private val LightColorScheme = lightColorScheme(
    onPrimaryContainer = PetroleumLightTheme,
    onSecondaryContainer = MediumBlueLightTheme,
    background = LighterBlueLightTheme,
    onBackground = DarkBlueDarkTheme,
    primary = PetroleumLightTheme,
    onPrimary = DarkBlueDarkTheme,
    secondary = DarkBlueLightTheme,
    onSecondary = LighterBlueLightTheme,
    tertiary = MediumBlueLightTheme,
    onTertiary = PetroleumBlueDarkTheme,
    error = DarkRed
)

@Composable
fun BookScapeComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}