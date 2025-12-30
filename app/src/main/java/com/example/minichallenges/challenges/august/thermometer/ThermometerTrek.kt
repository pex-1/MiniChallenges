package com.example.minichallenges.challenges.august.thermometer

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minichallenges.R
import com.example.minichallenges.challenges.august.theme.AugustTheme
import com.example.minichallenges.challenges.august.theme.primary
import com.example.minichallenges.challenges.august.theme.shadow
import com.example.minichallenges.challenges.august.theme.surface
import com.example.minichallenges.challenges.august.theme.surfaceHigher
import com.example.minichallenges.challenges.august.theme.textDisabled
import com.example.minichallenges.challenges.august.theme.textPrimary
import com.example.minichallenges.challenges.august.theme.textSecondary
import com.example.minichallenges.challenges.august.util.CheckIcon
import com.example.minichallenges.challenges.august.util.ThermometerIcon
import com.example.minichallenges.challenges.august.util.softShadow

@Composable
fun ThermometerTrekRoot(
    viewModel: ThermometerViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ThermometerTrek(state)
    {
        viewModel.onAction(it)
    }
}

@Composable
fun ThermometerTrek(
    state: ThermometerTrekState = ThermometerTrekState(),
    onAction: (ThermometerTrekAction) -> Unit = {}
) {
    val isTracking = state.trackingState == TrackingState.Tracking
    AugustTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(surface),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 26.dp)
                    .softShadow(shadow, 12.dp, 36.dp, 20.dp)
                    .width(360.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(surfaceHigher),
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 5.dp)
                        .background(surfaceHigher),
                    text = stringResource(R.string.thermometer_trek),
                    style = MaterialTheme.typography.titleLarge
                )
                if (state.trackingState == TrackingState.Idle) {
                    Text(
                        text = stringResource(R.string.press_start_to_begin_tracking_temperature),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 24.dp)
                            .background(surfaceHigher),
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val allTracked = state.temperaturesTracked.size == 20

                        Icon(
                            imageVector = CheckIcon,
                            contentDescription = null,
                            tint = if (allTracked) primary else textSecondary
                        )
                        Text(
                            modifier = Modifier.padding(start = 2.dp),
                            text = "${state.temperaturesTracked.size}/20",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(start = 24.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        TemperatureColumn(
                            temperatures = state.temperaturesTracked,
                            startIndex = 0,
                            modifier = Modifier.weight(1f)
                        )
                        TemperatureColumn(
                            temperatures = state.temperaturesTracked,
                            startIndex = 10,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 32.dp, bottom = 24.dp),
                    enabled = !isTracking,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary,
                        disabledContainerColor = surface
                    ),
                    onClick = {
                        if (state.trackingState == TrackingState.Idle) {
                            onAction(ThermometerTrekAction.OnStartClicked)
                        } else {
                            onAction(ThermometerTrekAction.OnResetClicked)
                        }
                    }
                ) {
                    Text(
                        text = state.trackingState.text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isTracking) textDisabled else surfaceHigher
                    )
                }
            }
        }
    }
}

@Composable
private fun TemperatureColumn(
    temperatures: List<Float?>,
    startIndex: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in startIndex until (startIndex + 10)) {
            key(i) {
                val temp = temperatures.getOrNull(i)

                Row(
                    modifier = Modifier
                        .height(18.dp)
                        .animateContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = ThermometerIcon,
                        contentDescription = null,
                        tint = if (temp == null) textDisabled else primary
                    )
                    if (temp != null) {
                        Text(
                            modifier = Modifier.padding(start = 2.dp),
                            text = stringResource(R.string.temp_format).format(temp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 15.sp,
                            color = textPrimary
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun ThermometerTrekPreview() {
    AugustTheme {
        ThermometerTrek()
    }
}