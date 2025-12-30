package com.example.minichallenges.challenges.august.orderqueueoutpost

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minichallenges.challenges.august.theme.AugustTheme
import com.example.minichallenges.challenges.august.theme.errorPulse
import com.example.minichallenges.challenges.august.theme.overload
import com.example.minichallenges.challenges.august.theme.primary
import com.example.minichallenges.challenges.august.theme.shadow
import com.example.minichallenges.challenges.august.theme.surface
import com.example.minichallenges.challenges.august.theme.surfaceHigher
import com.example.minichallenges.challenges.august.theme.surfaceHighest
import com.example.minichallenges.challenges.august.theme.textDisabled
import com.example.minichallenges.challenges.august.theme.textPrimary
import com.example.minichallenges.challenges.august.theme.textSecondary
import com.example.minichallenges.challenges.august.util.PauseIcon
import com.example.minichallenges.challenges.august.util.PlayIcon
import com.example.minichallenges.challenges.august.util.softShadow

@Composable
fun OrderQueueOutpostRoot(
    viewModel: OrderQueueOutpostViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    OrderQueueOutpost(state) {
        viewModel.onStartClick()
    }
}

@Composable
fun OrderQueueOutpost(
    state: OrderQueueOutpostState = OrderQueueOutpostState(),
    onClick: () -> Unit = {}
) {

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
                    .background(surfaceHigher)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 5.dp)
                        .background(surfaceHigher),
                    text = "Order Queue Outpost",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                if (state.status == Status.START) {
                    Text(
                        text = "Press Play to start processing orders.",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 24.dp)
                            .background(surfaceHigher),
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
                    ) {

                        Row(
                            modifier = Modifier
                                .padding(top = 20.dp, bottom = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Queue: ${state.orders}/25",
                                style = MaterialTheme.typography.bodySmall
                            )

                            Text(
                                text = "${((state.orders / 25f) * 100).toInt()}%",
                                style = MaterialTheme.typography.bodySmall,
                                color = textSecondary
                            )
                        }
                        val progress = state.orders / 25f
                        SegmentedProgressBar(
                            progress = progress,
                            segments = 3,
                            color = (QueueState.fromValue(progress)).displayColor
                        )
                    }
                }

                val processing = state.status == Status.PROCESSING
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 32.dp, bottom = 24.dp),

                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (processing) surfaceHighest else primary
                    ),
                    onClick = {
                        onClick()
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = if (processing) PauseIcon else PlayIcon,
                            contentDescription = null,
                            tint = if (processing) textPrimary else surfaceHigher
                        )
                        Text(
                            text = if (processing) "Pause" else "Start",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (processing) textPrimary else surfaceHigher
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SegmentedProgressBar(
    progress: Float,
    segments: Int,
    color: Color,
    modifier: Modifier = Modifier,
    height: Dp = 10.dp
) {
    val textMeasurer = rememberTextMeasurer()
    // animation if overflow exists
    val infiniteTransition = rememberInfiniteTransition(label = "borderPulse")
    val animatedPadding by infiniteTransition.animateFloat(
        initialValue = 2f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "borderPadding"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height + 20.dp)
    ) {
        val overflow = if (progress < 1f) 0f else (progress - 1f)
        val cornerRadius = height.toPx() / 2

        val progressWidth = size.width * (progress - 2 * overflow)
        val overflowWidth = size.width * overflow

        // pulse animation
        if (overflow > 0f) {
            drawRoundRect(
                color = errorPulse,
                // Draw a rectangle that is larger than the bar by the animatedPadding amount
                topLeft = Offset(-animatedPadding, -animatedPadding),
                size = Size(size.width + animatedPadding * 2, height.toPx() + animatedPadding * 2),
                cornerRadius = CornerRadius(cornerRadius + animatedPadding)
            )
        }

        // Background
        drawRoundRect(
            color = surfaceHighest,
            size = Size(size.width, height.toPx()),
            cornerRadius = CornerRadius(cornerRadius)
        )

        // Progress
        drawPath(
            path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(0f, 0f, progressWidth, height.toPx()),
                        topLeft = CornerRadius(cornerRadius),
                        bottomLeft = CornerRadius(cornerRadius),
                        topRight = CornerRadius(if (progress == 1f) cornerRadius else 0f),
                        bottomRight = CornerRadius(if (progress == 1f) cornerRadius else 0f)
                    )
                )
            },
            color = color
        )

        // Overflow
        drawPath(
            path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(
                            progressWidth,
                            0f,
                            progressWidth + overflowWidth,
                            height.toPx()
                        ),
                        topLeft = CornerRadius(0f),
                        bottomLeft = CornerRadius(0f),
                        topRight = CornerRadius(cornerRadius),
                        bottomRight = CornerRadius(cornerRadius)
                    )
                )
            },
            color = overload
        )

        // Dividers
        val dividerWidth = 1.dp.toPx()
        val fullWidth = if (progress < 1f) size.width else progressWidth
        repeat(segments - 1) { index ->
            val x = fullWidth * (index + 1) / segments
            drawRect(
                color = surfaceHigher,
                topLeft = Offset(x - dividerWidth / 2, 0f),
                size = Size(dividerWidth, height.toPx())
            )
        }
        if (overflow > 0) {
            drawRect(
                color = surfaceHigher,
                topLeft = Offset(progressWidth, 0f),
                size = Size(dividerWidth, height.toPx())
            )
            val textLayoutResult = textMeasurer.measure(
                text = "100%",
                style = TextStyle(color = textDisabled, fontSize = 12.sp)
            )
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(
                    x = progressWidth - (textLayoutResult.size.width / 2),
                    y = height.toPx() + 4.dp.toPx()
                )
            )
        }
    }
}

@Preview
@Composable
private fun ThermometerTrekPreview() {
    AugustTheme {
        OrderQueueOutpost(state = OrderQueueOutpostState(orders = 30, status = Status.START))
    }
}