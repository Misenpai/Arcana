package com.misenpai.arcana.presentation.ui.themes

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.misenpai.arcana.presentation.viewmodel.ArcanaViewModel

// Define the light color scheme
private val LightColors = lightColorScheme(
    primary = Color(0xFF00695C),    // Teal
    onPrimary = Color.White,
    secondary = Color(0xFF4A90E2),  // Blue
    onSecondary = Color.Black,
    background = Color.White,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE0E0E0) // Light gray
)

// Define the dark color scheme
private val DarkColors = darkColorScheme(
    primary = Color(0xFF80CBC4),    // Light teal
    onPrimary = Color.Black,
    secondary = Color(0xFFCE93D8),  // Light purple
    onSecondary = Color.Black,
    background = Color.Black,
    surface = Color.Black,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF1E2A44) // Dark blue-gray
)

// Local composition to provide the dark theme state
val LocalIsDarkTheme = compositionLocalOf { false }

@Composable
fun ArcanaTheme(
    darkTheme: Boolean = LocalIsDarkTheme.current,
    content: @Composable () -> Unit
) {
    // Select the target color scheme based on the theme
    val targetColors = if (darkTheme) DarkColors else LightColors

    // Animate each color in the scheme
    val primary by animateColorAsState(
        targetValue = targetColors.primary,
        animationSpec = tween(durationMillis = 500)
    )
    val onPrimary by animateColorAsState(
        targetValue = targetColors.onPrimary,
        animationSpec = tween(durationMillis = 500)
    )
    val secondary by animateColorAsState(
        targetValue = targetColors.secondary,
        animationSpec = tween(durationMillis = 500)
    )
    val onSecondary by animateColorAsState(
        targetValue = targetColors.onSecondary,
        animationSpec = tween(durationMillis = 500)
    )
    val background by animateColorAsState(
        targetValue = targetColors.background,
        animationSpec = tween(durationMillis = 500)
    )
    val surface by animateColorAsState(
        targetValue = targetColors.surface,
        animationSpec = tween(durationMillis = 100)
    )
    val onSurface by animateColorAsState(
        targetValue = targetColors.onSurface,
        animationSpec = tween(durationMillis = 500)
    )
    val surfaceVariant by animateColorAsState(
        targetValue = targetColors.surfaceVariant,
        animationSpec = tween(durationMillis = 500)
    )

    // Create an animated color scheme using the animated colors
    val animatedColorScheme = targetColors.copy(
        primary = primary,
        onPrimary = onPrimary,
        secondary = secondary,
        onSecondary = onSecondary,
        background = background,
        surface = surface,
        onSurface = onSurface,
        surfaceVariant = surfaceVariant
    )

    // Apply the animated color scheme to MaterialTheme
    CompositionLocalProvider(LocalIsDarkTheme provides darkTheme) {
        MaterialTheme(
            colorScheme = animatedColorScheme,
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