package com.example.project01_danp.ui.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.material.Typography

private val DarkColorPalette = darkColors(
    primary = CustomGreen, // Purple200,
    primaryVariant = CustomViolet, // Purple700,
    secondary = CustomOrange // Teal200
)

private val LightColorPalette = lightColors(
    primary = CustomGreen, // Teal200,
    primaryVariant = CustomViolet, // Purple700,
    secondary = CustomOrange, // Purple500

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun Project01_DANPTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    fontFamily: FontFamily = fontPacifico,
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    if (darkTheme) {
        systemUiController.setSystemBarsColor(
            color = Color.Black
        )
    } else {
        systemUiController.setSystemBarsColor(
            color = Color.White
        )
    }
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val typography = getTypography(fontFamily)

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}

fun getTypography(fontFamily: FontFamily) = Typography(
    defaultFontFamily = fontFamily
)