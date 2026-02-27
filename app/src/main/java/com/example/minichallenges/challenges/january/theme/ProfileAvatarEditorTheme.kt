package com.example.minichallenges.challenges.january.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val Background = Color(0xFFEFF1F4)
private val Surface = Color(0xFFFFFFFF)
private val SurfaceError = Color(0xFFFFEAF0)
private val SurfaceAlt = Color(0xFF13202F)
private val Outline = Color(0xFFC0CAD6)
private val TextPrimary = Color(0xFF0A131D)
private val TextSecondary = Color(0xFF344054)
private val TextOnPrimary = Color(0xFFFFFFFF)
private val PrimaryGradientStart = Color(0xFF8AA4FF)
private val PrimaryGradientEnd = Color(0xFF6184FF)
private val Error = Color(0xFFCE003E)

val ColorScheme.primaryGradientStart: Color
    get() = PrimaryGradientStart

val ColorScheme.primaryGradientEnd: Color
    get() = PrimaryGradientEnd

val ColorScheme.surfaceAlt: Color
    get() = SurfaceAlt

@Composable
fun ProfileAvatarEditorTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = PrimaryGradientStart,
            onPrimary = TextOnPrimary,
            background = Background,
            surface = Surface,
            error = Error,
            onError = SurfaceError,
            outline = Outline,
            onBackground = TextPrimary,
            onSurface = TextSecondary
        ), content = content
    )

}