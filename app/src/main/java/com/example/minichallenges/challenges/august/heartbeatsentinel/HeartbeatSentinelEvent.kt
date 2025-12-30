package com.example.minichallenges.challenges.august.heartbeatsentinel

sealed class HeartbeatSentinelEvent {

    data class ShowSnackBar(
        val station: Station,
        val isOnline: Boolean
    ) : HeartbeatSentinelEvent()
}