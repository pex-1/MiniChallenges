package com.example.minichallenges.challenges.january.holidaymoviecollection.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minichallenges.R
import com.example.minichallenges.challenges.january.holidaymoviecollection.data.entity.Movie
import com.example.minichallenges.challenges.january.theme.HolidayMovieCollectionTheme
import com.example.minichallenges.challenges.january.theme.JakartaSans
import com.example.minichallenges.challenges.january.theme.gradientEnd
import com.example.minichallenges.challenges.january.theme.gradientStart
import com.example.minichallenges.challenges.january.theme.textPlaceholder

@Preview
@Composable
private fun CreateCollectionScreenPreview() {
    HolidayMovieCollectionTheme {
        CreateCollectionScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCollectionScreen(onBack: () -> Unit = {}) {
    val viewModel: CreateCollectionViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CreateCollectionSideEffect.OnCollectionCreated -> {
                    onBack()
                }

                null -> {}
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Create Bundle",
                        fontFamily = JakartaSans,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    if (uiState.selectedMovies.isNotEmpty()) {
                        Text(
                            text = "${uiState.selectedMovies.size} movie selected",
                            fontFamily = JakartaSans,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = onBack, colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_left),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        )
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline,
                thickness = 1.dp,
                modifier = Modifier.align(
                    Alignment.TopCenter
                )
            )

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                return@Box
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {

                var isFocused by remember {
                    mutableStateOf(false)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                        .let { modifier ->
                            if (isFocused) {
                                modifier.border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = MaterialTheme.shapes.small
                                )
                            } else {
                                modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.surface,
                                        shape = MaterialTheme.shapes.small
                                    )
                            }
                        }
                        .onFocusChanged { focusable ->
                            isFocused = focusable.isFocused
                        }
                        .padding(8.dp)
                ) {
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp)
                            .padding(top = 6.dp),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        value = uiState.collectionName,
                        onValueChange = { query ->
                            viewModel.onIntent(
                                CreateCollectionIntent.UpdateCollectionName(
                                    query
                                )
                            )
                        },
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (uiState.collectionName.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Bundle Name",
                                        style = TextStyle(
                                            fontFamily = JakartaSans,
                                            fontSize = 18.sp,
                                            color = MaterialTheme.colorScheme.textPlaceholder
                                        )
                                    )
                                }
                            }

                            innerTextField()
                        },
                        textStyle = TextStyle(
                            fontFamily = JakartaSans,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                    )

                    Text(
                        text = "${uiState.collectionName.length}/40",
                        fontFamily = JakartaSans,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.textPlaceholder,
                        modifier = Modifier.align(Alignment.End)
                    )
                }

                val focusManager = LocalFocusManager.current
                val keyboardController = LocalSoftwareKeyboardController.current

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(uiState.allMovies.size) { index ->
                        val movie = uiState.allMovies[index]
                        val isSelected = uiState.selectedMovies.contains(movie)
                        MovieCardWithSelection(
                            isSelected = isSelected,
                            movie = movie,
                            onSelection = { selected ->
                                focusManager.clearFocus(true)
                                keyboardController?.hide()

                                if (selected) {
                                    viewModel.onIntent(
                                        CreateCollectionIntent.AddMovieToCollection(
                                            movie = movie
                                        )
                                    )
                                } else {
                                    viewModel.onIntent(
                                        CreateCollectionIntent.RemoveMovieFromCollection(
                                            movie = movie
                                        )
                                    )
                                }
                            },
                            modifier = Modifier
                        )
                    }
                }
            }

            TextButton(
                onClick = {
                    viewModel.onIntent(
                        CreateCollectionIntent.CreateCollection
                    )
                },
                enabled = uiState.selectedMovies.isNotEmpty() && uiState.collectionName.isNotBlank(),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8F)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Save Bundle",
                    fontFamily = JakartaSans,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}

@Composable
fun MovieCardWithSelection(
    movie: Movie,
    enableSelectionFeature: Boolean = true,
    isSelected: Boolean = false,
    onSelection: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .let {
                    if (isSelected) {
                        it.border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.medium
                        )
                    } else it
                }
                .clip(shape = MaterialTheme.shapes.medium)
                .graphicsLayer {
                    alpha = if (isSelected) {
                        0.7F
                    } else {
                        1F
                    }
                }
                .clickable(enabled = enableSelectionFeature) {
                    onSelection(!isSelected)
                }
        ) {
            Image(
                painter = painterResource(movie.imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            if (enableSelectionFeature) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .size(28.dp)
                        .let {
                            if (!isSelected) {
                                it
                                    .background(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        shape = CircleShape
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = CircleShape
                                    )
                            } else {
                                it.background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.gradientStart,
                                            MaterialTheme.colorScheme.gradientEnd,
                                        )
                                    ),
                                    shape = CircleShape
                                )
                            }
                        }
                        .clip(CircleShape)
                        .clickable(onClick = { onSelection(!isSelected) }),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSelected) {
                        Icon(
                            painter = painterResource(R.drawable.ic_check),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = movie.name,
            fontFamily = JakartaSans,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}