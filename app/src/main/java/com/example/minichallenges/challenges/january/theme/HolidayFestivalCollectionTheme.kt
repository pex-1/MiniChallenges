package com.example.minichallenges.challenges.january.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Background = Color(0xFF0A131D)
private val SurfaceInput = Color(0xFF0E1B29)
private val SurfaceDisabled = Color(0xFF21354E)
private val Outline = Color(0xFF1C2C3F)
private val TextPrimary = Color(0xFFFFFFFF)
private val TextSecondary = Color(0xFFC1D2E5)
private val TextPlaceholder = Color(0xFF536D89)
private val Primary = Color(0xFF57B7FC)
private val PrimaryGradientStart = Color(0xFF57B7FC)
private val PrimaryGradientEnd = Color(0xFF0C77C4)

val ColorScheme.textPlaceholder: Color
    get() = TextPlaceholder

val ColorScheme.gradientStart: Color
    get() = PrimaryGradientStart

val ColorScheme.gradientEnd: Color
    get() = PrimaryGradientEnd

@Composable
fun HolidayMovieCollectionTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = darkColorScheme(
        background = Background,
        surface = SurfaceInput,
        surfaceVariant = SurfaceDisabled,
        outline = Outline,
        onBackground = TextPrimary,
        onSurface = TextPrimary,
        onSurfaceVariant = TextSecondary,
        primary = Primary,
    ), content = content)
}