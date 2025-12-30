package com.example.minichallenges.challenges.august.parcelpigeonrace

import java.io.File

data class PigeonImage(
    val position: Int,
    val file: File? = null,
    val size: Double = 0.0,
    val durationInMilSeconds: Long = 0L,
    val isLoading: Boolean = true
)
