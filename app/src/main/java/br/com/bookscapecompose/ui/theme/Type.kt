package br.com.bookscapecompose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import br.com.bookscapecompose.R

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily(listOf(Font(R.font.kavoon))),
        fontSize = 35.sp,
        lineHeight = 40.sp
    ),

    headlineMedium = TextStyle(
        fontFamily = FontFamily((listOf(Font(R.font.kavoon)))),
        fontSize = 28.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        lineHeight = 32.sp,
    ),

    headlineSmall = TextStyle(
        fontFamily = FontFamily(listOf(Font(R.font.kavoon))),
        fontSize = 26.sp,
        lineHeight = 30.sp
    ),

    displaySmall = TextStyle(
        fontFamily = FontFamily(listOf(Font(R.font.kavoon))),
        fontSize = 18.sp,
        lineHeight = 22.sp
    ),

    titleSmall = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 25.sp
    ),

    titleMedium = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    ),

    titleLarge = TextStyle(
        fontSize = 27.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 30.sp,
        textAlign = TextAlign.Center,
    ),

    labelSmall = TextStyle(
        fontSize = 15.sp,
        lineHeight = 20.sp
    ),

    labelMedium = TextStyle(
        fontSize = 18.sp,
        lineHeight = 22.sp
    ),

    bodySmall = TextStyle(
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),

    bodyMedium = TextStyle(
        fontSize = 22.sp,
        lineHeight = 26.sp,
        textAlign = TextAlign.Justify,
    ),

    bodyLarge = TextStyle(
        fontSize = 23.sp,
        lineHeight = 28.sp,
        textAlign = TextAlign.Center,
    ),
)