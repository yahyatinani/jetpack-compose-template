package com.why.template.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColors(
    primary = Blue700,
    primaryVariant = Blue900,
    onPrimary = Color.White,
    secondary = Blue700,
    secondaryVariant = Blue900,
    onSecondary = Color.White,
    error = Red800
)

private val DarkColors = darkColors(
    primary = Blue300,
    primaryVariant = Blue700,
    onPrimary = Color.Black,
    secondary = Blue300,
    onSecondary = Color.White,
    error = Red200
)

private fun getAppropriateColors(darkTheme: Boolean) = when {
    darkTheme -> DarkColors
    else -> LightColors
}

@Composable
fun TemplateTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = getAppropriateColors(isDarkTheme),
        typography = TemplateTypography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
