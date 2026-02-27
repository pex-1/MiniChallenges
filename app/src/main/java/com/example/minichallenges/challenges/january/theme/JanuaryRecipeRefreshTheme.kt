package com.example.minichallenges.challenges.january.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.minichallenges.R

private val Background = Color(0xFFFFFDF9)
private val SurfaceInput = Color(0x4DDEDBD4)
private val Outline = Color(0xFFDEDBD4)
private val Overlay = Color(0xB3080603)
private val TextPrimary = Color(0xFF011A0E)
private val TextSecondary = Color(0xFF595447)
private val TextOnPrimary = Color(0xFFFFFFFF)

val InstrumentSans = FontFamily(
    Font(R.font.instrument_sans_regular, FontWeight.Normal),
    Font(R.font.instrument_sans_medium, FontWeight.Medium),
    Font(R.font.instrument_sans_semibold, FontWeight.SemiBold),
    Font(R.font.instrument_sans_bold, FontWeight.Bold),
)

val InstrumentSerif = FontFamily(
    Font(R.font.instrument_serif_regular, FontWeight.Normal),
)

@Composable
fun JanuaryRecipeRefreshTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            background = Background,
            surface = Background,
            surfaceVariant = SurfaceInput,
            outline = Outline,
            scrim = Overlay,
            onPrimary = TextOnPrimary,
            primary = TextPrimary,
            secondary = TextSecondary
        ), content = content
    )
}