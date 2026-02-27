package com.example.minichallenges.challenges.january.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.minichallenges.R

private val BgMainGradientStart = Color(0xFFDAEEFF)
private val BgMainGradientEnd = Color(0xFFEDF7FF)
private val BgGallery = Color(0xFFFFFFFF)
private val BgErrorGradientStart = Color(0xFFFFE9EE)
private val BgErrorGradientEnd = Color(0xFFFFFBFC)
private val TextPrimary = Color(0xFF001221)
private val TextCard = Color(0xFFFFFFFF)
private val OutlineBtn = Color(0xFFC9D0D8)
private val Primary = Color(0xFF5799FC)
private val Error = Color(0xFFF7164B)

val ColorScheme.bgMainGradientStart: Color
    get() = BgMainGradientStart

val ColorScheme.bgMainGradientEnd: Color
    get() = BgMainGradientEnd

val ColorScheme.bgGallery: Color
    get() = BgGallery

val ColorScheme.bgErrorGradientStart: Color
    get() = BgErrorGradientStart

val ColorScheme.bgErrorGradientEnd: Color
    get() = BgErrorGradientEnd

val ColorScheme.textCard: Color
    get() = TextCard

val JakartaSans = FontFamily(
    Font(R.font.plus_jakarta_sans_bold, FontWeight.Bold),
    Font(R.font.plus_jakarta_sans_semibold, FontWeight.SemiBold),
    Font(R.font.plus_jakarta_sans_medium, FontWeight.Medium),
    Font(R.font.plus_jakarta_sans_regular, FontWeight.Normal)
)

@Composable
fun JanuaryTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = lightColorScheme(
        primary = Primary,
        error = Error,
        onPrimary = TextPrimary,
        onError = TextPrimary,
        outline = OutlineBtn
    ), content = content)
}