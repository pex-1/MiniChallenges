package com.example.minichallenges.challenges.august.orderqueueoutpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class OrderQueueOutpostViewModel : ViewModel() {
    private val _state = MutableStateFlow(OrderQueueOutpostState())
    var state = _state.asStateFlow()

    val orderChanel = Channel<Int>(capacity = 30)

    fun onStartClick() {
        when (state.value.status) {
            Status.START -> onStart()
            Status.PAUSED -> startOrStopProcessing()
            Status.PROCESSING -> startOrStopProcessing()
        }

    }

    private fun onStart() {
        _state.update {
            it.copy(
                status = Status.PROCESSING,
                orders = 0
            )
        }
        startProducing()
        startConsuming()
    }

    private fun startProducing() {
        viewModelScope.launch {
            val orderIds = (1..50).toList()
            orderIds.forEach {
                orderChanel.send(it)
                if (state.value.orders < 30) {
                    _state.update { currentState ->
                        currentState.copy(
                            orders = currentState.orders + 1
                        )
                    }
                }
                delay(250)
            }
        }
    }

    private fun startConsuming() {
        viewModelScope.launch {
            for (order in orderChanel) {
                delay(Random.nextLong(100, 250))
                if (state.value.status == Status.PAUSED) break
                if (state.value.orders > 1) {
                    _state.update { currentState ->
                        currentState.copy(
                            orders = currentState.orders - 1
                        )
                    }
                }
            }
        }
    }

    private fun startOrStopProcessing() {
        when (state.value.status) {
            Status.START -> Unit
            Status.PAUSED -> {
                _state.update {
                    it.copy(status = Status.PROCESSING)
                }
                startConsuming()
            }

            Status.PROCESSING -> {
                _state.update {
                    it.copy(status = Status.PAUSED)
                }
            }
        }
    }

}