package com.example.minichallenges.challenges.august.heartbeatsentinel

import com.example.minichallenges.august_2025.heartbeat_sentinel.ShowingOrNot
import com.example.minichallenges.august_2025.heartbeat_sentinel.ShowingOrNot.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HeartbeatSentinelRepository {

    private val heartbeatDuration = 50L
    fun emitStatus(
        type: Station,
    ): Flow<ShowingOrNot> = flow {
        while (true) {
            emit(NOT_SHOWING)
            delay(heartbeatDuration)

            emit(SHOWING)
            delay(type.timeElapsed - heartbeatDuration)
        }
    }
}




















