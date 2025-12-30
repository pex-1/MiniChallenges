package com.example.minichallenges.challenges.august.livetickeraggregator

sealed interface LiveTickerAggregatorAction {

    data object OnPauseResume: LiveTickerAggregatorAction

    data object OnBreakXETRA: LiveTickerAggregatorAction
}