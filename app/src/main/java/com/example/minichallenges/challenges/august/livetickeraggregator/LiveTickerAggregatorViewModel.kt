package com.example.minichallenges.challenges.august.livetickeraggregator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minichallenges.challenges.august.livetickeraggregator.ChangeStatus.*
import com.example.minichallenges.challenges.august.livetickeraggregator.LiveTickerAggregatorAction.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.round
import kotlin.random.Random

class LiveTickerAggregatorViewModel: ViewModel() {

    private val _state = MutableStateFlow(LiveTickerAggregatorState())
    val state: StateFlow<LiveTickerAggregatorState> = _state

    private var lastEmissionTime = 0L

    val initialFeeds = listOf(
        Exchange("BSE", initialPrice = 99.95, updateDelayMs = 2000),
        Exchange("HKEX", initialPrice = 98.00, updateDelayMs = 2000),
        Exchange("JPX", initialPrice = 96.80, updateDelayMs = 4000),
        Exchange("LSE", initialPrice = 105.70, updateDelayMs = 5000),
        Exchange("NASDAQ", initialPrice = 134.10, updateDelayMs = 3000),
        Exchange("NYSE", initialPrice = 118.35, updateDelayMs = 3000),
        Exchange("SSE", initialPrice = 110.25, updateDelayMs = 5000),
        Exchange("SIX", initialPrice = 115.50, updateDelayMs = 4000),
        Exchange("TSX", initialPrice = 122.90, updateDelayMs = 3000),
        Exchange("XETRA", initialPrice = 140.40, updateDelayMs = 1000),
    )
    val feeds = MutableStateFlow(initialFeeds)

    init {
        initialFeeds.forEachIndexed { position, exc ->
            viewModelScope.launch {
                var exchange = exc
                while (true) {
                    delay(exchange.updateDelayMs)
                    if (exchange.name == "XETRA" && _state.value.isXETRABroken) continue

                    val changeStatus = entries.random()
                    val newPrice = when (changeStatus) {
                        NO_CHANGE -> exchange.currentPrice
                        INCREASE -> (round((exchange.currentPrice + Random.nextDouble(0.01, 1.00)) * 100) / 100)
                        DECREASE -> (round((exchange.currentPrice - Random.nextDouble(0.01, 1.00)) * 100) / 100)
                    }
                    exchange = exchange.copy(
                        initialPrice = exchange.currentPrice,
                        currentPrice = newPrice,
                        lastTimeUpdated = System.currentTimeMillis()
                    )

                    feeds.update { current ->
                        current.toMutableList().also { it[position] = exchange }
                    }
                }
            }
        }

        viewModelScope.launch {
            while (isActive) {
                val now = System.currentTimeMillis()
                if (_state.value.isRunning && now - lastEmissionTime >= _state.value.updatesRate) {
                    _state.update { current ->
                        val sortedList = feeds.value.map { it.copy() }.sortedByDescending { it -> it.isBroken }
                        current.copy(exchanges = sortedList)
                    }

                    lastEmissionTime = now
                }
                delay(50)
            }
        }
    }

    fun onAction(action: LiveTickerAggregatorAction) {
        when (action) {
            OnPauseResume -> {
                _state.update { current ->
                    current.copy(
                        isRunning = !current.isRunning
                    )
                }
            }

            OnBreakXETRA -> {
                feeds.update { current ->
                    current.mapIsXETRABroken(true)
                }
                _state.update { current ->
                    current.copy(
                        exchanges = current.exchanges.mapIsXETRABroken(true).sortedByDescending { it -> it.isBroken },
                        isXETRABroken = true
                    )
                }
                viewModelScope.launch {
                    delay(4000)
                    feeds.update { current ->
                        current.mapIsXETRABroken(false)
                    }
                    _state.update { current ->
                        current.copy(
                            exchanges = current.exchanges.mapIsXETRABroken(false),
                            isXETRABroken = false
                        )
                    }
                }
            }
        }
    }

    fun List<Exchange>.mapIsXETRABroken(isBroken: Boolean): List<Exchange> {
        return map { item ->
            if (item.name == "XETRA") {
                item.copy(
                    isBroken = isBroken,
                )
            } else {
                item.copy()
            }
        }
    }
}