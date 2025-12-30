package com.example.minichallenges.challenges.august.thermometer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minichallenges.challenges.august.data.temperatureList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ThermometerViewModel : ViewModel() {
    private val _state = MutableStateFlow(ThermometerTrekState())
    val state = _state.asStateFlow()

    fun onAction(action: ThermometerTrekAction) {
        when (action) {
            is ThermometerTrekAction.OnStartClicked -> {
                _state.update {
                    it.copy(trackingState = TrackingState.Tracking)
                }
                onStartPressed()
            }

            ThermometerTrekAction.OnResetClicked -> {
                _state.update {
                    it.copy(temperaturesTracked = emptyList(), trackingState = TrackingState.Tracking)
                }
                onStartPressed()
            }
        }
    }

    fun onStartPressed() {
        viewModelScope.launch {
            temperatureFlow(temperatureList).filter { it > -50 }.take(20)
                .map { it * 9 / 5 + 32 }
                .collect { value ->
                    _state.update {
                        val updatedTemperatures = it.temperaturesTracked + value
                        it.copy(
                            temperaturesTracked = updatedTemperatures
                        )
                    }
                }
            _state.update {
                it.copy(trackingState = TrackingState.Complete)
            }
        }
    }

    fun temperatureFlow(
        values: List<Float>,
        delay: Long = 250
    ): Flow<Float> = flow {
        values.forEach {
            emit(it)
            kotlinx.coroutines.delay(delay)
        }
    }
}