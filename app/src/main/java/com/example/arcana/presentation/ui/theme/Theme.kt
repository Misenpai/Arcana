package com.example.arcana.presentation.ui.themes

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.example.arcana.presentation.viewmodel.ArcanaViewModel

// Define light color scheme
private val LightColors = lightColorScheme(
    primary = Color(0xFF00695C),
    onPrimary = Color.White,
    secondary = Color(0xFF4A90E2),
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = Color.Black
)

// Define dark color scheme
private val DarkColors = darkColorScheme(
    primary = Color(0xFF80CBC4),
    onPrimary = Color.Black,
    secondary = Color(0xFFCE93D8),
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White
)

// Local composition for theme state
val LocalIsDarkTheme = compositionLocalOf { false }

// Base theme composable
@Composable
fun ArcanaTheme(
    darkTheme: Boolean = LocalIsDarkTheme.current,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    CompositionLocalProvider(LocalIsDarkTheme provides darkTheme) {
        MaterialTheme(
            colorScheme = colors,
            typography = MaterialTheme.typography,
            shapes = MaterialTheme.shapes,
            content = content
        )
    }
}

// Theme composable that integrates with ViewModel
@Composable
fun ArcanaThemeWithViewModel(
    viewModel: ArcanaViewModel,
    content: @Composable () -> Unit
) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    ArcanaTheme(darkTheme = isDarkTheme) {
        content()
    }
}