package com.example.arcana.presentation.ui.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF00695C),
    onPrimary = androidx.compose.ui.graphics.Color.White,
    secondary = androidx.compose.ui.graphics.Color(0xFF4A90E2),
    onSecondary = androidx.compose.ui.graphics.Color.Black,
    background = androidx.compose.ui.graphics.Color.White,
    onBackground = androidx.compose.ui.graphics.Color.Black
)

private val DarkColors = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF80CBC4),
    onPrimary = androidx.compose.ui.graphics.Color.Black,
    secondary = androidx.compose.ui.graphics.Color(0xFFCE93D8),
    onSecondary = androidx.compose.ui.graphics.Color.Black,
    background = androidx.compose.ui.graphics.Color(0xFF121212),
    onBackground = androidx.compose.ui.graphics.Color.White
)

@Composable
fun ArcanaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}