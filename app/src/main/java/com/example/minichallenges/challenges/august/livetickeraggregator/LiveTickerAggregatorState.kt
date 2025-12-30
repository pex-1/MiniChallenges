package com.example.minichallenges.challenges.august.livetickeraggregator

data class LiveTickerAggregatorState(
    val exchanges: List<Exchange> = emptyList(),
    val isRunning: Boolean = true,
    val isXETRABroken: Boolean = false,
    val updatesRate: Long = 250
)
