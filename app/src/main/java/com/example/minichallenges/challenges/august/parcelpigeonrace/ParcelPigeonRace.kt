package com.example.minichallenges.challenges.august.parcelpigeonrace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.minichallenges.challenges.august.theme.AugustTheme
import com.example.minichallenges.challenges.august.theme.primary
import com.example.minichallenges.challenges.august.theme.shadow
import com.example.minichallenges.challenges.august.theme.surface
import com.example.minichallenges.challenges.august.theme.surfaceHigher
import com.example.minichallenges.challenges.august.theme.textDisabled
import com.example.minichallenges.challenges.august.theme.textPrimary
import com.example.minichallenges.challenges.august.util.softShadow
import java.text.DecimalFormat

@Composable
fun ParcelPigeonRaceRoot(
    viewModel: ParcelPigeonRaceViewModel = viewModel(
        factory = ParcelPigeonRaceViewModel.Factory(ParcelPigeonRaceRepository())
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val cacheDir = LocalContext.current.cacheDir
    ParcelPigeonRace(state)
    {
        viewModel.runOrRunAgain(cacheDir)
    }
}

@Composable
fun ParcelPigeonRace(
    state: ParcelPigeonRaceState = ParcelPigeonRaceState(),
    onAction: () -> Unit = {}
) {
    AugustTheme {
        if (state.status == Status.CAN_RUN) {
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
                        text = "Parcel Pigeon Race",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "Press Run Again to fetch images",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 24.dp)
                            .background(surfaceHigher),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 32.dp, bottom = 24.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary,
                            disabledContainerColor = surface
                        ),
                        onClick = {
                            onAction()
                        }
                    ) {
                        Text(
                            text = "Run Loading",
                            style = MaterialTheme.typography.bodyMedium,
                            color = surfaceHigher
                        )
                    }
                }
            }
        } else {
            ParcelPigeonRaceRunningOrRunAgain(state) {
                onAction()
            }
        }
    }
}

@Composable
fun ParcelPigeonRaceRunningOrRunAgain(
    state: ParcelPigeonRaceState,
    modifier: Modifier = Modifier,
    onRunAgain: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(surface)
            .padding(top = 65.dp)
            .padding(horizontal = 26.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Parcel Pigeon Race",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        state.images.forEach {
            PigeonImageUi(it)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Total time:",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.weight(1f))

            val longestTime = state.images.maxOf { it.durationInMilSeconds }
            val totalTime = when {
                longestTime == 0L -> "…"
                else -> {
                    "%.1fs".format(longestTime.toFloat() / 1000)
                }
            }
            Text(
                text = totalTime,
                style = MaterialTheme.typography.bodySmall,
                color = textPrimary,
            )
            Spacer(modifier = Modifier.width(24.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(12.dp),
            enabled = state.status != Status.RUNNING,
            colors = ButtonDefaults.buttonColors(
                containerColor = primary,
                disabledContainerColor = surfaceHigher,
                contentColor = surfaceHigher,
                disabledContentColor = textDisabled,
            ),
            onClick = {
                onRunAgain()
            }
        ) {
            Text(
                text = "Run Again",
                style = MaterialTheme.typography.bodyMedium,
                color = if (state.status != Status.RUNNING) surfaceHigher else textDisabled
            )
        }
    }
}

@Composable
fun PigeonImageUi(
    pigeonImage: PigeonImage,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(surfaceHigher, RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier.size(60.dp)
        ) {
            when {
                pigeonImage.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize()
                    )
                }

                pigeonImage.file != null -> AsyncImage(
                    model = pigeonImage.file,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Inside
                )

                else -> Unit
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Image#%1d".format(pigeonImage.position + 1),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(5.dp))

            val size = when {
                pigeonImage.isLoading -> "Fetching..."
                else -> "${DecimalFormat("#.##").format(pigeonImage.size)}MB"
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = size,
                    style = MaterialTheme.typography.bodySmall,
                )

                if (!pigeonImage.isLoading) {
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "%.1fs".format(pigeonImage.durationInMilSeconds.toFloat() / 1000),
                        style = MaterialTheme.typography.bodySmall,
                    )

                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}


@Preview
@Composable
private fun ParcelPigeonRacePreview() {
    AugustTheme {
        ParcelPigeonRace()
    }
}