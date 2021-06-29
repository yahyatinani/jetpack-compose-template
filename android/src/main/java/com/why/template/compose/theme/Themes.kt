package com.why.template.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightTheme = lightColors(
    primary = Blue700,
    primaryVariant = Blue900,
    onPrimary = Color.White,
    secondary = Blue700,
    secondaryVariant = Blue900,
    onSecondary = Color.White,
    error = Red800
)

private val DarkTheme = darkColors(
    primary = Blue300,
    primaryVariant = Blue700,
    secondary = Blue300,
    error = Red200
)

private fun colors(darkTheme: Boolean) = when {
    darkTheme -> DarkTheme
    else -> LightTheme
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
