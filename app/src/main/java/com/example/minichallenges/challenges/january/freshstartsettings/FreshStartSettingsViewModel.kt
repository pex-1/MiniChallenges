package com.example.minichallenges.challenges.january.freshstartsettings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class FreshStartSettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FreshStartSettingsRepository.getInstance(application)

    private val _uiState = MutableStateFlow(FreshStartSettingsState())
    val uiState: StateFlow<FreshStartSettingsState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getDataFlow().collect { state ->
                _uiState.value = state
            }
        }
    }

    fun onIntent(intent: FreshStartSettingsIntent) {
        viewModelScope.launch {
            when (intent) {
                is FreshStartSettingsIntent.UpdateTheme -> {
                    if (uiState.value.selectedTheme != intent.theme) {
                        repository.updateTheme(intent.theme)
                    }
                }

                is FreshStartSettingsIntent.UpdateNotificationsEnabled -> {
                    if (uiState.value.isNotificationsEnabled != intent.isEnabled) {
                        repository.updateNotificationsEnabled(intent.isEnabled)
                    }
                }

                is FreshStartSettingsIntent.UpdateDailyStepGoal -> {
                    if (uiState.value.dailyStepGoal != intent.stepGoal) {
                        repository.updateDailyStepGoal(intent.stepGoal)
                    }
                }

                is FreshStartSettingsIntent.UpdateMotivationLevel -> {
                    if (uiState.value.motivationLevel != intent.motivationLevel) {
                        repository.updateMotivationLevel(intent.motivationLevel)
                    }
                }
            }
        }
    }
}

sealed interface FreshStartSettingsIntent {
    data class UpdateTheme(val theme: NewYearTheme) : FreshStartSettingsIntent
    data class UpdateNotificationsEnabled(val isEnabled: Boolean) : FreshStartSettingsIntent
    data class UpdateDailyStepGoal(val stepGoal: Int) : FreshStartSettingsIntent
    data class UpdateMotivationLevel(val motivationLevel: Float) : FreshStartSettingsIntent
}