package com.example.minichallenges.challenges.august.livetickeraggregator

data class Exchange(
    val name: String,
    val initialPrice: Double,
    val currentPrice: Double = initialPrice,
    val lastTimeUpdated: Long = 0,
    val isBroken: Boolean = false,
    val updateDelayMs: Long
)