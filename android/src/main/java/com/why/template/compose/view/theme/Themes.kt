package com.why.template.compose.view.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/*
 * https://material.io/design/color/the-color-system.html#color-theme-creation
 */
private val lightColorsPalette = lightColors(
    primary = Blue700,
    primaryVariant = Blue900,
    secondary = Green600,
    secondaryVariant = Green800,
    background = Color.White,
    surface = Color.White,
    error = Red800,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White
)

private val darkColorsPalette = darkColors(
    primary = Blue500,
    primaryVariant = Blue300,
    secondary = Green600,
    secondaryVariant = Green300,
    background = Color.Black,
    surface = Grey900,
    error = Red200,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black

)

private fun colors(darkTheme: Boolean) = when {
    darkTheme -> darkColorsPalette
    else -> lightColorsPalette
}

@Composable
fun MyTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = colors(isDarkTheme),
        typography = TemplateTypography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
