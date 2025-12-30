package com.example.minichallenges.challenges.august.util

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp

fun Modifier.softShadow(
    color: Color,
    offsetY: Dp,
    blurRadius: Dp,
    cornerRadius: Dp
) = this.drawBehind {
    drawIntoCanvas { canvas ->
        val paint = Paint().asFrameworkPaint().apply {
            this.color = color.toArgb()
            maskFilter = BlurMaskFilter(
                blurRadius.toPx(),
                BlurMaskFilter.Blur.NORMAL
            )
        }

        val left = 0f
        val top = offsetY.toPx()
        val right = size.width
        val bottom = size.height + offsetY.toPx()

        canvas.nativeCanvas.drawRoundRect(
            left,
            top,
            right,
            bottom,
            cornerRadius.toPx(),
            cornerRadius.toPx(),
            paint
        )
    }
}