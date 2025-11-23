package com.example.minichallenges.challenges.september

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollable2DState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.scrollable2D
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import com.example.minichallenges.R
import com.example.minichallenges.challenges.september.data.festivalSchedule
import com.example.minichallenges.challenges.september.theme.Overlay
import com.example.minichallenges.challenges.september.theme.SeptemberTheme
import com.example.minichallenges.challenges.september.theme.lime
import com.example.minichallenges.challenges.september.theme.orange
import com.example.minichallenges.challenges.september.theme.purple
import kotlinx.coroutines.delay

data class Session(
    val artistName: String,
    val genre: MusicGenre,
    val startTime: Pair<Int, Int>,
    val endTime: Pair<Int, Int>,
    val color: Color
)

enum class MusicGenre(val displayName: String, val color: Color) {
    Main("Main", orange),
    Rock("Rock", purple),
    Electro("Electro", lime)
}

@Composable
fun MultiStageTimelinePainter() {

    val stages = MusicGenre.entries
    var scale by remember { mutableFloatStateOf(1f) }

    var offset by remember { mutableStateOf(Offset.Zero) }
    var size by remember { mutableStateOf(IntSize.Zero) }
    var maxOffset by remember { mutableStateOf(Offset.Zero) }
    val scrollState = rememberScrollState()
    var headerHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    val zoomPercentage by remember {
        derivedStateOf {
            (scale * 10).toInt() * 10
        }
    }
    var showHint by rememberSaveable {
        mutableStateOf(true)
    }
    if (showHint) {
        LaunchedEffect(Unit) {
            delay(2500L)
            showHint = false
        }
    }

    SeptemberTheme {
        Scaffold { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(20.dp),
                    text = stringResource(R.string.festival_schedule),
                    style = MaterialTheme.typography.bodySmall
                )

                val state = rememberTransformableState { zoomChange, panChange, _ ->
                    scale = (scale * zoomChange).coerceIn(1F, 2.5F)

                    val extraWidth = (scale - 1) * size.width
                    val extraHeight = (scale - 1) * size.height

                    val maxX = extraWidth / 2
                    val maxY = extraHeight / 2
                    maxOffset = Offset(x = maxX, y = maxY)

                    offset = Offset(
                        x = (offset.x + panChange.x).coerceIn(-maxX, maxX),
                        y = (offset.y + panChange.y).coerceIn(-maxY, maxY)
                    )
                }

                val scrollable2DState = rememberScrollable2DState { delta ->
                    var consumedX = 0f
                    var consumedY = 0f

                    val newX = offset.x + delta.x
                    if (newX in -maxOffset.x..maxOffset.x) {
                        offset = offset.copy(x = newX)
                        consumedX = delta.x
                    } else {
                        offset = offset.copy(x = newX.coerceIn(-maxOffset.x, maxOffset.x))
                        consumedX = delta.x - (newX - offset.x)
                    }

                    if (scale > 1f) {
                        val newY = offset.y + delta.y
                        if (newY in -maxOffset.y..maxOffset.y) {
                            offset = offset.copy(y = newY)
                            consumedY = delta.y
                        } else {
                            val clampedY = newY.coerceIn(-maxOffset.y, maxOffset.y)
                            val remaining = newY - clampedY
                            offset = offset.copy(y = clampedY)

                            consumedY = delta.y - remaining + scrollState.dispatchRawDelta(-remaining)
                        }
                    } else {
                        consumedY = scrollState.dispatchRawDelta(-delta.y)
                    }

                    Offset(consumedX, consumedY)
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .transformable(state)
                            .verticalScroll(scrollState, enabled = scale == 1f)
                            .scrollable2D(scrollable2DState, enabled = scale != 1f)
                            .clipToBounds()
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                translationX = offset.x
                                translationY = offset.y
                            }
                            .onGloballyPositioned {
                                size = it.size
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned {
                                    headerHeight = with(density) { it.size.height.toDp() }
                                }
                                .padding(start = 56.dp, end = 12.dp, bottom = 12.dp, top = 6.dp)
                        ) {
                            stages.forEach {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = it.displayName,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.labelMedium
                                        .copy(fontWeight = FontWeight.SemiBold)
                                )
                            }
                        }

                        HorizontalDivider(
                            thickness = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        BackgroundGrid(modifier = Modifier.fillMaxSize())
                    }

                    if (scale == 1f && showHint) {
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = headerHeight)
                                .background(MaterialTheme.colorScheme.Overlay.copy(alpha = 0.75f))
                                .clickable(
                                    interactionSource = null,
                                    indication = null
                                ) {
                                    showHint = false
                                }
                                .wrapContentSize(Alignment.Center),
                            text = stringResource(R.string.pinch_to_zoom),
                            color = MaterialTheme.colorScheme.surface,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                    // zoom indicator
                    this@Column.AnimatedVisibility(
                        visible = scale > 1f,
                        modifier = Modifier
                            .zIndex(1f)
                            .alpha(if (scale == 1f) 0f else 1f)
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp)
                    ) {
                        ZoomIndicator(zoomPercentage)
                    }
                }
            }
        }
    }
}

private fun getHours(): List<Pair<Int, Int>> {
    val times = mutableListOf<Pair<Int, Int>>()
    for (hour in 12..22) {
        times.add(Pair(hour, 0))
        times.add(Pair(hour, 30))
    }
    times.add(Pair(23, 0))
    return times
}


@Composable
fun BackgroundGrid(
    modifier: Modifier = Modifier,
    leftPadding: Dp = 56.dp,
    topPadding: Dp = 28.dp,
    rightPadding: Dp = 12.dp,
    columnHeight: Dp = 50.dp
) {
    val hours = getHours()
    val lineColor = MaterialTheme.colorScheme.outline
    val totalContentHeight = ((hours.size - 1) * columnHeight.value).dp + topPadding

    Box(
        modifier = modifier
    ) {

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = leftPadding, end = rightPadding)
                .drawBehind {
                    val columnWidth = size.width / 3
                    val columnHeightInPx = columnHeight.toPx()

                    // Vertical column lines
                    for (col in 0..MusicGenre.entries.size) {
                        val x = col * columnWidth
                        drawLine(
                            color = lineColor,
                            start = Offset(x, 0f),
                            end = Offset(x, totalContentHeight.toPx()),
                            strokeWidth = 2f
                        )
                    }

                    // horizontal + padding
                    val topPadding = topPadding.toPx()
                    hours.forEachIndexed { index, _ ->
                        val y = index * columnHeightInPx + topPadding
                        drawLine(
                            color = lineColor,
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = 2f
                        )
                    }
                }) {

            // draw schedule
            val density = LocalDensity.current
            festivalSchedule.forEach { session ->
                val startingBlock = (session.startTime.first - hours.first().first) * 2 +
                        (session.startTime.second / 30)
                val numberOfBlocks = (session.endTime.first - session.startTime.first) * 2 +
                        (session.endTime.second - session.startTime.second) / 30

                val cardHeight = numberOfBlocks * columnHeight
                val columnWidth = constraints.maxWidth / 3
                val widthInDp = with(density) { columnWidth.toDp() }
                val positionY = topPadding + columnHeight * startingBlock

                val positionX = session.genre.ordinal * widthInDp

                Box(
                    modifier = Modifier.offset(positionX, positionY)
                ) {
                    EventCard(
                        session,
                        cardHeight,
                        widthInDp
                    )
                }
            }

        }
        // Hour text labels
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 12.dp, top = topPadding - 8.dp)
        ) {
            getHours().filterIndexed { index, _ -> index % 2 == 0 }.forEachIndexed { index, time ->
                Text(
                    modifier = Modifier
                        .padding(
                            top = if (index == 0) 0.dp else 80.dp,
                            bottom = if (time.first == 23) 12.dp else 0.dp
                        ),
                    text = stringResource(R.string.hours_format).format(time.first),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
fun ZoomIndicator(percentage: Int) {
    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.Overlay.copy(alpha = 0.75f))
            .padding(8.dp),
        text = stringResource(R.string.zoom_format).format(percentage),
        style = MaterialTheme.typography.bodySmall
            .copy(color = MaterialTheme.colorScheme.surface),
        fontSize = 16.sp
    )
}

@Composable
private fun formatTime(startTime: Pair<Int, Int>, endTime: Pair<Int, Int>): String {
    return stringResource(R.string.card_time_format).format(
        startTime.first,
        startTime.second,
        endTime.first,
        endTime.second
    )
}

@Composable
fun EventCard(
    session: Session,
    columnHeight: Dp,
    columnWidth: Dp
) {
    Column(
        modifier = Modifier
            .height(columnHeight)
            .width(columnWidth)
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(session.color)
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = formatTime(session.startTime, session.endTime),
            style = MaterialTheme.typography.labelSmall
                .copy(color = MaterialTheme.colorScheme.primary),
            fontSize = 12.sp
        )

        Text(
            modifier = Modifier.padding(horizontal = 6.dp),
            text = session.artistName,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BackgroundGridPreview() {
    SeptemberTheme {
        MultiStageTimelinePainter()
    }
}