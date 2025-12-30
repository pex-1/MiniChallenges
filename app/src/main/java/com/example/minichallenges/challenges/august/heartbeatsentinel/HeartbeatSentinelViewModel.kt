package com.example.minichallenges.august_2025.heartbeat_sentinel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minichallenges.august_2025.heartbeat_sentinel.OnlineOrOffline.*
import com.example.minichallenges.challenges.august.heartbeatsentinel.HeartbeatSentinelEvent
import com.example.minichallenges.challenges.august.heartbeatsentinel.HeartbeatSentinelRepository
import com.example.minichallenges.challenges.august.heartbeatsentinel.Station
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HeartbeatSentinelViewModel(
    private val repository: HeartbeatSentinelRepository = HeartbeatSentinelRepository()
): ViewModel() {

    private val onlineOrOfflineA = MutableStateFlow(ONLINE)
    private val onlineOrOfflineB = MutableStateFlow(ONLINE)
    private val onlineOrOfflineC = MutableStateFlow(ONLINE)

    private val stationA = MutableStateFlow(StateStation(Station.A))
    private val stationB = MutableStateFlow(StateStation(Station.B))
    private val stationC = MutableStateFlow(StateStation(Station.C))

    private val eventChannel = Channel<HeartbeatSentinelEvent>()
    val events = eventChannel.receiveAsFlow()

    val state: StateFlow<HeartbeatSentinelState> =
        combine(stationA, stationB, stationC) { a, b, c ->
            val offlineFirst = listOf(a, b, c)
                .filter {
                    it.onlineOrOffline == OFFLINE
                }
                .minByOrNull {
                    it.lastOnline
                }
            HeartbeatSentinelState(
                stationA = a,
                stationB = b,
                stationC = c,
                offlineFirst = offlineFirst
            )
        }.stateIn(viewModelScope, SharingStarted.Eagerly, HeartbeatSentinelState())

    init {
        viewModelScope.launch {
            val isOfflineA = onlineOrOfflineA
                .map {
                    Pair(Station.A, it == OFFLINE)
                }
            val isOfflineB = onlineOrOfflineB
                .map {
                    Pair(Station.B, it == OFFLINE)
                }
            val isOfflineC = onlineOrOfflineC
                .map {
                    Pair(Station.C, it == OFFLINE)
                }
                .distinctUntilChanged()

            merge(isOfflineA.drop(1), isOfflineB.drop(1), isOfflineC.drop(1))
                .distinctUntilChanged()
                .collectLatest {
                    eventChannel.send(
                        HeartbeatSentinelEvent.ShowSnackBar(
                            it.first,
                            !it.second
                        )
                    )
            }
        }

        viewModelScope.launch {
            onlineOrOfflineA
                .map {//is ONLINE or PARTIALLY_ONLINE
                    it == ONLINE || it == PARTIALLY_ONLINE
                }
                .distinctUntilChanged()
                .collectLatest { isOnlineOrPartiallyOnline ->
                    if (isOnlineOrPartiallyOnline) {
                        repository.emitStatus(
                            stationA.value.type,
                        ).collectLatest {
                            val lastOnline = if (stationA.value.onlineOrOffline == ONLINE) System.currentTimeMillis() else stationA.value.lastOnline
                            val newOnlineOrOffline = if (System.currentTimeMillis() - lastOnline < 2000) stationA.value.onlineOrOffline else OFFLINE
                            stationA.value = stationA.value.copy(
                                showing = it,
                                lastOnline = lastOnline,
                                onlineOrOffline = newOnlineOrOffline
                            )
                            onlineOrOfflineA.value = newOnlineOrOffline
                        }
                    }
                }
        }

        viewModelScope.launch {
            onlineOrOfflineB
                .map {//is ONLINE or PARTIALLY_ONLINE
                    it == ONLINE || it == PARTIALLY_ONLINE
                }
                .distinctUntilChanged()
                .collectLatest { isOnlineOrPartiallyOnline ->
                    if (isOnlineOrPartiallyOnline) {
                        repository.emitStatus(
                            stationB.value.type,
                        ).collectLatest {
                            val lastOnline = if (stationB.value.onlineOrOffline == ONLINE) System.currentTimeMillis() else stationB.value.lastOnline
                            val newOnlineOrOffline = if (System.currentTimeMillis() - lastOnline < 2000) stationB.value.onlineOrOffline else OFFLINE
                            stationB.value = stationB.value.copy(
                                showing = it,
                                lastOnline = lastOnline,
                                onlineOrOffline = newOnlineOrOffline
                            )
                            onlineOrOfflineB.value = newOnlineOrOffline
                        }
                    }
                }
        }

        viewModelScope.launch {
            onlineOrOfflineC
                .map {//is ONLINE or PARTIALLY_ONLINE
                    it == ONLINE || it == PARTIALLY_ONLINE
                }
                .distinctUntilChanged()
                .collectLatest { isOnlineOrPartiallyOnline ->
                    if (isOnlineOrPartiallyOnline) {
                        repository.emitStatus(
                            stationC.value.type,
                        ).collectLatest {
                            val lastOnline = if (onlineOrOfflineC.value == ONLINE) System.currentTimeMillis() else stationC.value.lastOnline
                            val newOnlineOrOffline = if (System.currentTimeMillis() - lastOnline < 2000) onlineOrOfflineC.value else OFFLINE
                            stationC.value = stationC.value.copy(
                                showing = it,
                                lastOnline = lastOnline,
                                onlineOrOffline = newOnlineOrOffline
                            )
                            onlineOrOfflineC.value = newOnlineOrOffline
                        }
                    }
                }
        }

        viewModelScope.launch {
            delay(4000)

            onlineOrOfflineC.value = PARTIALLY_ONLINE

            delay(4000)

            onlineOrOfflineC.value = ONLINE

            delay(3000)

            onlineOrOfflineC.value = PARTIALLY_ONLINE

            delay(8000)

            onlineOrOfflineC.value = ONLINE
        }
    }

}