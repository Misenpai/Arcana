package com.example.arcana.presentation.ui.themes

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.example.arcana.presentation.viewmodel.ArcanaViewModel

// Define light color scheme (unchanged from your current white mode)
private val LightColors = lightColorScheme(
    primary = Color(0xFF00695C),       // Teal for primary elements
    onPrimary = Color.White,
    secondary = Color(0xFF4A90E2),     // Blue for secondary elements
    onSecondary = Color.Black,
    background = Color.White,          // White background
    surface = Color.White,             // White surface (e.g., cards, app bar)
    onSurface = Color.Black,           // Black text/icons on surface
    surfaceVariant = Color(0xFFE0E0E0) // Light gray for subtle variations
)

// Define dark color scheme (updated for your requirements)
private val DarkColors = darkColorScheme(
    primary = Color(0xFF80CBC4),       // Light teal for primary elements
    onPrimary = Color.Black,
    secondary = Color(0xFFCE93D8),     // Purple for secondary elements
    onSecondary = Color.Black,
    background = Color.Black,          // Black background
    surface = Color.Black,             // Black surface (e.g., app bar)
    onSurface = Color.White,           // White text/icons on surface
    surfaceVariant = Color(0xFF1E2A44) // Dark blue for cards (e.g., #1E2A44)
)

val LocalIsDarkTheme = compositionLocalOf { false }

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