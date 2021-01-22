package com.why.template.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColors(
    primary = Red700,
    primaryVariant = Red900,
    onPrimary = Color.White,
    secondary = Red700,
    secondaryVariant = Red900,
    onSecondary = Color.White,
    error = Red800
)

private val DarkColors = darkColors(
    primary = Red300,
    primaryVariant = Red700,
    onPrimary = Color.Black,
    secondary = Red300,
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
