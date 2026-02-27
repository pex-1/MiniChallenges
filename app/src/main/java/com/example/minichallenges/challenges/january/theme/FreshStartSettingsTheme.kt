package com.example.minichallenges.challenges.january.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Background = Color(0xFFEDF2F8)
private val Surface = Color(0xFFFFFFFF)
private val SurfaceInput = Color(0xFFF5F8FA)
private val Outline = Color(0xFFC9D6E0)
private val OutlineInput = Color(0xFFE7EDF1)
private val TextPrimary = Color(0xFF101828)
private val TextSecondary = Color(0xFF3F4D63)
private val Primary = Color(0xFF50ADF5)

val ColorScheme.surfaceInput: Color
    get() = SurfaceInput

val ColorScheme.outlineInput: Color
    get() = OutlineInput

@Composable
fun FreshStartSettingsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            background = Background,
            surface = Surface,
            onSurface = TextPrimary,
            primary = Primary,
            outline = Outline,
            onPrimary = TextPrimary,
            onSecondary = TextSecondary,
        ),
        content = content
    )
}