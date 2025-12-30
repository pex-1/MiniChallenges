package com.example.minichallenges.challenges.august.thermometer

enum class TrackingState(val text: String) {
    Idle("Start"),
    Tracking("Tracking..."),
    Complete("Reset")
}