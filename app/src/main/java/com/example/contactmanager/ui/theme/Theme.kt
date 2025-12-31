package com.example.contactmanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = TealSecondary,
    tertiary = AmberAccent,
    background = LightBackground,
    surface = LightSurface
)

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimary,
    secondary = TealSecondary,
    tertiary = AmberAccent,
    background = DarkBackground,
    surface = DarkSurface
)

@Composable
fun ContactManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}
