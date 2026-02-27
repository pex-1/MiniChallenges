package com.example.minichallenges.challenges.january.profileavatareditor

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.minichallenges.challenges.january.theme.surfaceAlt

@Composable
fun AvatarEditorComponent(
    offset: Offset,
    size: Size,
    onOffsetChange: (Offset) -> Unit,
    onSizeChange: (Size) -> Unit,
    graphicsLayer: GraphicsLayer,
    uri: Uri,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .widthIn(max = 300.dp)
            .aspectRatio(1F)
            .background(color = MaterialTheme.colorScheme.surfaceAlt)
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(uri)
                .build()
        )

        Image(
            painter = painter,
            contentDescription = "Avatar Image",
            modifier = Modifier
                .matchParentSize()
                .drawWithContent {
                    graphicsLayer.record {
                        this@drawWithContent.drawContent()
                    }
                    drawLayer(graphicsLayer)
                },
            contentScale = ContentScale.FillWidth
        )

        var overlaySize by remember {
            mutableStateOf(Size.Zero)
        }
        val minSize = with(LocalDensity.current) { 100.dp.toPx() }

        val state = rememberTransformableState { zoomChange, panChange, rotationChange ->
            onSizeChange(
                size.copy(
                    width = (size.width * zoomChange).coerceIn(minSize, overlaySize.width),
                    height = (size.height * zoomChange).coerceIn(minSize, overlaySize.height)
                )
            )
            val newOffsetX = (offset.x + panChange.x).coerceIn(
                0F,
                overlaySize.width - size.width
            )
            val newOffsetY = (offset.y + panChange.y).coerceIn(
                0F,
                overlaySize.height - size.height
            )
            onOffsetChange(
                offset.copy(
                    x = newOffsetX,
                    y = newOffsetY
                )
            )
        }
        ImageOverlay(
            offset = offset,
            size = size,
            modifier = Modifier
                .matchParentSize()
                .onGloballyPositioned {
                    overlaySize = it.size.toSize()
                }
                .transformable(state)
        )
    }
}

@Composable
fun ImageOverlay(offset: Offset, size: Size, modifier: Modifier = Modifier) {
    val overlayColor = Color(0xB20A131D)
    val outline = MaterialTheme.colorScheme.outline
    val highlightColor = MaterialTheme.colorScheme.onPrimary

    Box(
        modifier = modifier
            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            .drawWithCache {
                val borderWidth = 3.dp.toPx()
                val length = 16.dp.toPx()

                val startX = offset.x
                val startY = offset.y
                val endX = offset.x + size.width
                val endY = offset.y + size.height

                val borderPath = Path().apply {
                    moveTo(startX, startY + length)
                    lineTo(startX, startY)
                    lineTo(startX + length, startY)

                    moveTo(endX - length, startY)
                    lineTo(endX, startY)
                    lineTo(endX, startY + length)

                    moveTo(startX, endY - length)
                    lineTo(startX, endY)
                    lineTo(startX + length, endY)

                    moveTo(endX - length, endY)
                    lineTo(endX, endY)
                    lineTo(endX, endY - length)
                }
                val threePart = size.width / 3F

                onDrawBehind {
                    this.drawRect(
                        color = overlayColor,
                        topLeft = Offset.Zero,
                        size = this.size
                    )

                    this.drawRect(
                        color = Color.Unspecified,
                        topLeft = offset,
                        size = size,
                        blendMode = BlendMode.Clear
                    )

                    this.drawRect(
                        color = outline,
                        topLeft = offset,
                        size = size,
                        style = Stroke(
                            1.dp.toPx()
                        )
                    )

                    this.drawCircle(
                        color = outline,
                        radius = size.width / 2,
                        center = Offset(
                            x = offset.x + size.width / 2,
                            y = offset.y + size.height / 2
                        ),
                        style = Stroke(
                            1.dp.toPx()
                        )
                    )

                    repeat(2) {
                        drawLine(
                            color = outline,
                            start = Offset(
                                x = offset.x + threePart * (it + 1),
                                y = offset.y
                            ),
                            end = Offset(
                                x = offset.x + threePart * (it + 1),
                                y = offset.y + size.height
                            ),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                    repeat(2) {
                        drawLine(
                            color = outline,
                            start = Offset(
                                x = offset.x,
                                y = offset.y + threePart * (it + 1)
                            ),
                            end = Offset(
                                x = offset.x + size.width,
                                y = offset.y + threePart * (it + 1)
                            ),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                    drawPath(
                        path = borderPath,
                        color = highlightColor,
                        style = Stroke(borderWidth, cap = StrokeCap.Round)
                    )
                }
            }
    )
}