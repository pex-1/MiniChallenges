package com.example.minichallenges.challenges.september.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColorScheme = lightColorScheme(
    primary = textPrimary,
    background = surface,
    surface = surface,
    outline = outline,
)

val ColorScheme.Overlay
    @Composable get() = overlay
@Composable
fun SeptemberTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}