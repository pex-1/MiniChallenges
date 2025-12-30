package com.example.minichallenges.challenges.august.thermometer

sealed interface ThermometerTrekAction {
    data object OnStartClicked : ThermometerTrekAction
    data object OnResetClicked : ThermometerTrekAction
}