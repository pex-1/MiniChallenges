package com.example.minichallenges.challenges.august.orderqueueoutpost

import androidx.compose.ui.graphics.Color
import com.example.minichallenges.challenges.august.theme.error
import com.example.minichallenges.challenges.august.theme.primary
import com.example.minichallenges.challenges.august.theme.warning

enum class QueueState(val start: Float, val end: Float) {
    GREEN(0f, 0.33f),
    YELLOW(0.34f, 0.66f),
    RED(0.67f, 1f);

    val displayColor: Color
        get() = when (this) {
            GREEN -> primary
            YELLOW -> warning
            RED -> error
        }

    companion object {
        fun fromValue(value: Float): QueueState {
            return entries.find { value.coerceIn(0f, 1f) in it.start..it.end } ?: GREEN
        }
    }
}