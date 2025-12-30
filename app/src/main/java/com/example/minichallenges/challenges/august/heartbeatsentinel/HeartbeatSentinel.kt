package com.example.minichallenges.challenges.august.heartbeatsentinel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.minichallenges.R
import com.example.minichallenges.august_2025.Primary
import com.example.minichallenges.august_2025.SurfaceHigher
import com.example.minichallenges.august_2025.TextPrimary
import com.example.minichallenges.august_2025.TextSecondary
import com.example.minichallenges.challenges.august.heartbeatsentinel.HeartbeatSentinelEvent.*
import com.example.minichallenges.august_2025.heartbeat_sentinel.HeartbeatSentinelState
import com.example.minichallenges.august_2025.heartbeat_sentinel.HeartbeatSentinelViewModel
import com.example.minichallenges.august_2025.heartbeat_sentinel.OnlineOrOffline.*
import com.example.minichallenges.august_2025.heartbeat_sentinel.ShowingOrNot
import com.example.minichallenges.august_2025.heartbeat_sentinel.StateStation
import com.example.minichallenges.challenges.august.theme.surface
import com.example.minichallenges.ui.ObserveAsEvents
import com.example.minichallenges.ui.theme.HostGroteskMedium
import com.example.minichallenges.ui.theme.HostGroteskNormalRegular
import com.example.minichallenges.ui.theme.HostGroteskSemiBold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun HeartbeatSentinelRoot(
    viewModel: HeartbeatSentinelViewModel = HeartbeatSentinelViewModel()
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(surface)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        HeartbeatSentinel(
            viewModel = viewModel,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun HeartbeatSentinel(
    modifier: Modifier = Modifier,
    viewModel: HeartbeatSentinelViewModel
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val online = stringResource(R.string.heartbeat_sentinel_is_online)
    val isOffline = stringResource(R.string.heartbeat_sentinel_is_offline)
    val stationA = stringResource(R.string.heartbeat_sentinel_station_a)
    val stationB = stringResource(R.string.heartbeat_sentinel_station_b)
    val stationC = stringResource(R.string.heartbeat_sentinel_station_c)

    val getSnackBarMessage: (station: Station, isOnline: Boolean) -> String = { station: Station, isOnline: Boolean ->
        val station = when (station) {
            Station.A -> stationA
            Station.B -> stationB
            Station.C -> stationC
        }
        if (isOnline) {
            "$station $online"
        } else {
            "$station $isOffline"
        }
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ShowSnackBar -> {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = getSnackBarMessage(event.station, event.isOnline),
                    )
                }
            }
        }
    }

    val state by viewModel.state.collectAsState()

    HeartbeatSentinel(
        snackBarHostState = snackBarHostState,
        state = state,
        modifier = modifier
    )
}

@Composable
fun HeartbeatSentinel(
    snackBarHostState: SnackbarHostState,
    state: HeartbeatSentinelState,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier
            ) { data ->
                HeartbeatSentinelSnackBar(
                    message = data.visuals.message,
                    color = if (state.offlineFirst?.onlineOrOffline == OFFLINE) Error else Primary
                )

                LaunchedEffect(data) {
                    delay(1500)
                    snackBarHostState.currentSnackbarData?.dismiss()
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.heartbeat_sentinel_title),
                style = HostGroteskSemiBold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            var stationOffline by remember { mutableStateOf<Pair<Int, String>?>(null) }

            val firstStationOffline = state.offlineFirst

            LaunchedEffect(firstStationOffline) {
                if (firstStationOffline == null) {
                    stationOffline = null
                } else {
                    val station = firstStationOffline.type.getStringUi()
                    while (true) {
                        val time = ((System.currentTimeMillis() - firstStationOffline.lastOnline).toDouble() / 1000.0 * 10).toInt() / 10.0
                        stationOffline = Pair(station, time.toString())
                        delay(1.milliseconds)
                    }
                }
            }

            val text = stationOffline?.let {
                stringResource(R.string.heartbeat_sentinel_offline, stringResource(it.first), it.second)
            } ?: run {
                stringResource(R.string.heartbeat_sentinel_operational)
            }

            Text(
                text = text,
                style = HostGroteskNormalRegular,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                HeartbeatSentinelStation(
                    modifier = Modifier.weight(1f),
                    textRes = R.string.heartbeat_sentinel_station_a,
                    color = state.stationA.getColor()
                )

                Spacer(modifier = Modifier.width(8.dp))

                HeartbeatSentinelStation(
                    modifier = Modifier.weight(1f),
                    textRes = R.string.heartbeat_sentinel_station_b,
                    color = state.stationB.getColor()
                )

                Spacer(modifier = Modifier.width(8.dp))

                HeartbeatSentinelStation(
                    modifier = Modifier.weight(1f),
                    textRes = R.string.heartbeat_sentinel_station_c,
                    color = state.stationC.getColor()
                )
            }
        }
    }
}

@Composable
fun HeartbeatSentinelSnackBar(
    message: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = color,
        tonalElevation = 6.dp,
        shadowElevation = 4.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Success",
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

fun StateStation.getColor(): Color {
    return when{
        onlineOrOffline == OFFLINE -> Error
        showing == ShowingOrNot.NOT_SHOWING -> SurfaceHigher
        else -> Primary
    }
}

@Composable
fun HeartbeatSentinelStation(
    textRes: Int,
    color: Color,
    modifier: Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceHigher),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .aspectRatio(1f)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_station),
                tint = color,
                contentDescription = stringResource(textRes),
                modifier = Modifier
                    .size(20.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(textRes),
                style = HostGroteskMedium.copy(
                    fontSize = 19.sp,
                    lineHeight = 24.sp,
                ),
                color = TextPrimary
            )
        }
    }
}