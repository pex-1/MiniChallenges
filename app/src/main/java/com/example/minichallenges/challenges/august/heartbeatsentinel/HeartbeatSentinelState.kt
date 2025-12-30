package com.example.minichallenges.august_2025.heartbeat_sentinel

import com.example.minichallenges.august_2025.heartbeat_sentinel.OnlineOrOffline.*
import com.example.minichallenges.august_2025.heartbeat_sentinel.ShowingOrNot.*
import com.example.minichallenges.challenges.august.heartbeatsentinel.Station

data class HeartbeatSentinelState(
    val stationA: StateStation = StateStation(Station.A),
    val stationB: StateStation = StateStation(Station.B),
    val stationC: StateStation = StateStation(Station.C),
    val offlineFirst: StateStation? = null
)

data class StateStation(
    val type: Station,
    val showing: ShowingOrNot = SHOWING,
    val onlineOrOffline: OnlineOrOffline = ONLINE,
    val lastOnline: Long = 0
)

enum class ShowingOrNot {
    SHOWING,
    NOT_SHOWING
}

enum class OnlineOrOffline {
    ONLINE,
    PARTIALLY_ONLINE,
    OFFLINE
}