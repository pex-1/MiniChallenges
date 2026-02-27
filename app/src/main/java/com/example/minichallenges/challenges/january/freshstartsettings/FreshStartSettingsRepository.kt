package com.example.minichallenges.challenges.january.freshstartsettings

import android.content.Context
import androidx.annotation.FloatRange
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.freshStartSettingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "fresh_start_settings"
)

class FreshStartSettingsRepository private constructor(
    private val context: Context
) {
    companion object {
        @Volatile
        private var instance: FreshStartSettingsRepository? = null

        fun getInstance(context: Context): FreshStartSettingsRepository {
            return instance ?: synchronized(this) {
                instance ?: FreshStartSettingsRepository(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    private val notificationKey = booleanPreferencesKey("notifications_enabled")
    private val themeKey = stringPreferencesKey("selected_theme")
    private val stepGoalKey = intPreferencesKey("daily_step_goal")
    private val motivationLevelKey = floatPreferencesKey("motivation_level")
    private val lastUpdatedAtKey = longPreferencesKey("last_updated_at")

    fun getDataFlow(): Flow<FreshStartSettingsState> {
        return context.freshStartSettingsDataStore.data.map { prefs ->
            FreshStartSettingsState(
                isNotificationsEnabled = prefs[notificationKey] ?: false,
                selectedTheme = runCatching {
                    NewYearTheme.valueOf(prefs[themeKey] ?: NewYearTheme.COZY_FIREPLACE.name)
                }.getOrElse { NewYearTheme.COZY_FIREPLACE },
                dailyStepGoal = prefs[stepGoalKey] ?: 6000,
                motivationLevel = prefs[motivationLevelKey] ?: 0.5F,
                lastUpdatedAt = prefs[lastUpdatedAtKey]
            )
        }
    }

    suspend fun updateTheme(theme: NewYearTheme) {
        context.freshStartSettingsDataStore.edit {
            it[themeKey] = theme.name
            it[lastUpdatedAtKey] = System.currentTimeMillis()
        }
    }

    suspend fun updateNotificationsEnabled(isEnabled: Boolean) {
        context.freshStartSettingsDataStore.edit {
            it[notificationKey] = isEnabled
            it[lastUpdatedAtKey] = System.currentTimeMillis()
        }
    }

    suspend fun updateDailyStepGoal(stepGoal: Int) {
        context.freshStartSettingsDataStore.edit {
            it[stepGoalKey] = stepGoal
            it[lastUpdatedAtKey] = System.currentTimeMillis()
        }
    }

    suspend fun updateMotivationLevel(@FloatRange(from = 0.0, to = 1.0) level: Float) {
        context.freshStartSettingsDataStore.edit {
            it[motivationLevelKey] = level.coerceIn(0f, 1f)
            it[lastUpdatedAtKey] = System.currentTimeMillis()
        }
    }
}

data class FreshStartSettingsState(
    val isNotificationsEnabled: Boolean = false,
    val selectedTheme: NewYearTheme = NewYearTheme.COZY_FIREPLACE,
    val dailyStepGoal: Int = 6000,
    val motivationLevel: Float = 0.5F,
    val lastUpdatedAt: Long? = null,
)

enum class NewYearTheme(val label: String) {
    COZY_FIREPLACE("Cozy Fireplace"),
    SNOWY_MOUNTAINS("Snowy Mountains"),
    CITY_LIGHTS("City Lights"),
}
