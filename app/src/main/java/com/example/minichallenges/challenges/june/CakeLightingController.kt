package com.example.minichallenges.challenges.june

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.minichallenges.R
import kotlinx.coroutines.delay

data class Candle(
    val iconRes: Int,
    val name: String
)

private val Candles = listOf(
    Candle(R.drawable.candle_1, "Candle 1"),
    Candle(R.drawable.candle_2, "Candle 2"),
    Candle(R.drawable.candle_7, "Candle 7"),
    Candle(R.drawable.candle_3, "Candle 3"),
    Candle(R.drawable.candle_4, "Candle 4"),
    Candle(R.drawable.candle_5, "Candle 5"),
    Candle(R.drawable.candle_6, "Candle 6")
)

@Composable
fun CakeLightScreen(modifier: Modifier = Modifier) {

    val candleStates = remember {
        mutableStateListOf<Boolean>().apply {
            repeat(Candles.size) { add(true) }
        }
    }

    val allLighten = candleStates.all { it }
    val noneLighten = candleStates.none { it }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF113345)),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // text
        if (allLighten) {
            Text(
                text = "Ready for cake \uD83C\uDF89",
                color = Color(0xFFFFF5EB),
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = maliFamily,
                fontWeight = FontWeight.Bold,
            )
        } else if (noneLighten) {
            Text(
                text = "Make a wish... \uD83D\uDCA8",
                color = Color(0xFFFFF5EB),
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = maliFamily,
                fontWeight = FontWeight.Bold,
            )
        } else {
            Spacer(modifier = Modifier.height(30.dp))
        }

        // candles
        CandlesLightingScreen(
            candleStates = candleStates,
            onCandleClick = { index ->
                candleStates[index] = !candleStates[index]
            },
            modifier = Modifier.fillMaxWidth()
        )

        // button
        if (!allLighten) {
            Button(
                onClick = {
                    val allLighten = candleStates.all { it }
                    for (i in candleStates.indices) {
                        candleStates[i] = !allLighten
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF95D3ED)
                )
            ) {
                Text(
                    text = "Light all candles",
                    color = Color(0xFF113345),
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = maliFamily,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        } else {
            Spacer(modifier = Modifier.height(55.dp))
        }
    }
}

@Preview
@Composable
private fun CakeLightScreenPreview() {
    CakeLightScreen()
}

@Composable
fun CandleScreen(
    onClick: () -> Unit,
    isLighten: Boolean = true,
    @DrawableRes candleRes: Int,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(isLighten) {
        if (!isLighten) {
            delay(5000)
            onClick()
        }
    }

    Column(
        modifier = modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLighten) {
            Image(
                modifier = Modifier
                    .animateContentSize()
                    .clickable {
                        if (isLighten) {
                            onClick()
                        }
                    },
                painter = painterResource(R.drawable.candle_state_on),
                contentDescription = null
            )
        } else {
            Image(
                modifier = Modifier
                    .animateContentSize()
                    .clickable {
                        onClick()
                    },
                painter = painterResource(R.drawable.candle_state_off),
                contentDescription = null
            )
        }
        Image(
            modifier = Modifier
                .animateContentSize()
                .clickable {
                    onClick()
                },
            painter = painterResource(candleRes),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CandleScreenPreview() {
    CandleScreen(
        onClick = { },
        candleRes = R.drawable.candle_1
    )
}

@Composable
fun CandlesLightingScreen(
    candleStates: List<Boolean>,
    onCandleClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        var index = 0
        while (index < Candles.size) {
            if (index % 3 == 0) {
                Box(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    contentAlignment = Alignment.Center
                ) {
                    val currentIndex = index
                    CandleScreen(
                        onClick = { onCandleClick(currentIndex) },
                        candleRes = Candles[currentIndex].iconRes,
                        isLighten = candleStates[currentIndex]
                    )
                    index++
                }
            } else {
                Column {
                    repeat(2) {
                        if (index < Candles.size) {
                            val currentIndex = index
                            CandleScreen(
                                onClick = { onCandleClick(currentIndex) },
                                candleRes = Candles[currentIndex].iconRes,
                                isLighten = candleStates[currentIndex]
                            )
                            index++
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CandlesLightingScreenPreview() {
    val previewStates = remember { mutableStateListOf(true, false, true, false, true, false, true) }
    CandlesLightingScreen(
        candleStates = previewStates,
        onCandleClick = {}
    )
}