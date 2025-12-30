package com.example.minichallenges.challenges.august.thermometer

data class ThermometerTrekState(
    val trackingState: TrackingState = TrackingState.Idle,
    val temperaturesTracked: List<Float> = emptyList()
)