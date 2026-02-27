package com.example.minichallenges.challenges.january.wintertravelgallery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.minichallenges.R
import com.example.minichallenges.challenges.january.state.Destination
import com.example.minichallenges.challenges.january.theme.JakartaSans
import com.example.minichallenges.challenges.january.theme.JanuaryTheme
import com.example.minichallenges.challenges.january.theme.bgErrorGradientEnd
import com.example.minichallenges.challenges.january.theme.bgErrorGradientStart
import com.example.minichallenges.challenges.january.theme.bgGallery
import com.example.minichallenges.challenges.january.theme.bgMainGradientEnd
import com.example.minichallenges.challenges.january.theme.bgMainGradientStart

@Preview
@Composable
private fun WinterTravelGalleryDetailPreview() {
    JanuaryTheme {
        WinterTravelGalleryDetail(Destination.entries.random())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WinterTravelGalleryDetail(destination: Destination, onBack: () -> Unit = {}) {
    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        destination.title,
                        fontFamily = JakartaSans,
                        fontSize = 28.sp,
                        lineHeight = 40.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.bgGallery
                ),
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable(onClick = onBack)
                            .clip(MaterialTheme.shapes.small)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_left),
                            contentDescription = "Back to Destinations screen",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }, containerColor = MaterialTheme.colorScheme.bgGallery
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(destination.imageUrls) { url ->
                DestinationImageCard(
                    url = url
                )
            }
        }
    }
}

@Composable
fun DestinationImageCard(url: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.bgGallery,
                shape = MaterialTheme.shapes.medium
            )
            .aspectRatio(.83F)
    ) {
        val context = LocalContext.current
        val imageRequest = remember(url) {
            ImageRequest.Builder(context)
                .data(url)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .networkCachePolicy(CachePolicy.ENABLED)
                .listener(onError = { request, error ->
                    error.throwable.printStackTrace()
                })
                .build()
        }

        SubcomposeAsyncImage(
            model = imageRequest,
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.bgMainGradientStart,
                                    MaterialTheme.colorScheme.bgMainGradientEnd,
                                ),
                            ), shape = MaterialTheme.shapes.medium
                        )
                        .clip(shape = MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        trackColor = MaterialTheme.colorScheme.bgGallery,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                    )
                }
            }, error = {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.bgErrorGradientStart,
                                    MaterialTheme.colorScheme.bgErrorGradientEnd,
                                ),
                            ), shape = MaterialTheme.shapes.medium
                        )
                        .clip(shape = MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_alert_triangle),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }, contentDescription = null, modifier = Modifier.matchParentSize()
        )
    }
}