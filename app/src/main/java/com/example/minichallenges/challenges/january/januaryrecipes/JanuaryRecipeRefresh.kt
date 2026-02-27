package com.example.minichallenges.challenges.january.januaryrecipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minichallenges.R
import com.example.minichallenges.challenges.january.theme.InstrumentSans
import com.example.minichallenges.challenges.january.theme.InstrumentSerif
import com.example.minichallenges.challenges.january.theme.JanuaryRecipeRefreshTheme
import com.example.minichallenges.challenges.january.theme.textPlaceholder

@Preview
@Composable
private fun JanuaryRecipeRefreshPreview() {
    JanuaryRecipeRefreshTheme {
        JanuaryRecipeRefresh()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JanuaryRecipeRefresh() {
    val viewModel: JanuaryRecipeRefreshViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackBarMessage.collect {
            snackBarHostState.showSnackbar("Added to Favourites")
        }
    }

    val gridState = rememberLazyGridState()

    // ✅ Scroll to top only when search changes (optional)
    LaunchedEffect(uiState.searchQuery) {
        gridState.scrollToItem(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_heart_rounded),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Added to Favourites",
                            fontSize = 16.sp,
                            fontFamily = InstrumentSans,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "January Recipes",
                        fontFamily = InstrumentSerif,
                        fontSize = 40.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val columnCount = if (maxWidth > 600.dp) 2 else 1
            val refreshState = rememberPullToRefreshState()

            PullToRefreshBox(
                isRefreshing = uiState.isLoading,
                state = refreshState,
                onRefresh = { viewModel.onIntent(JanuaryRecipeIntent.RefreshData) },
                indicator = {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = uiState.isLoading,
                        state = refreshState,
                        color = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.background
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp)
                ) {
                    SearchField(
                        query = uiState.searchQuery,
                        onQueryChange = { viewModel.onIntent(JanuaryRecipeIntent.UpdateSearchQuery(it)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    val keyboardController = LocalSoftwareKeyboardController.current
                    val focusManager = LocalFocusManager.current

                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F)
                            .padding(horizontal = 10.dp),
                        state = gridState,
                        columns = GridCells.Fixed(columnCount),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (uiState.recipes.isEmpty()) {
                            item(span = { GridItemSpan(columnCount) }) {
                                Text(
                                    text = "No recipes match your search",
                                    fontFamily = InstrumentSerif,
                                    fontSize = 28.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp)
                                )
                            }
                        } else {
                            items(
                                items = uiState.recipes,
                                key = { it.name } // ✅ stable key for enum
                            ) { recipe ->
                                RecipeCard(
                                    recipe = recipe,
                                    isFavourite = uiState.favourites.contains(recipe),
                                    onToggleFavourite = { fav ->
                                        focusManager.clearFocus(true)
                                        keyboardController?.hide()
                                        viewModel.onIntent(
                                            JanuaryRecipeIntent.ToggleFavourite(recipe, fav)
                                        )
                                    },
                                    onClick = {
                                        focusManager.clearFocus(true)
                                        keyboardController?.hide()
                                        viewModel.onIntent(JanuaryRecipeIntent.SelectRecipe(recipe))
                                    }
                                )
                            }
                        }
                    }
                }
            }

            uiState.activeRecipe?.let { recipe ->
                RecipeDetailDialog(
                    onDismissRequest = { viewModel.onIntent(JanuaryRecipeIntent.RemoveRecipe) },
                    recipe = recipe
                )
            }
        }
    }
}

@Composable
private fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.medium
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            fontFamily = InstrumentSans,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        decorationBox = { innerTextField ->
            if (query.isEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Search for recipes",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp,
                            fontFamily = InstrumentSans,
                            lineHeight = 24.sp,
                            letterSpacing = 0.5.sp
                        )
                    )
                }
            }
            innerTextField()
        }
    )
}

@Composable
fun RecipeCard(
    recipe: Recipe,
    isFavourite: Boolean,
    onToggleFavourite: (Boolean) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp, horizontal = 6.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.medium)
        ) {
            Image(
                painter = painterResource(recipe.imageRes),
                contentDescription = recipe.title,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            IconButton(
                onClick = { onToggleFavourite(!isFavourite) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.scrim
                )
            ) {
                Icon(
                    painter = painterResource(
                        if (isFavourite) R.drawable.ic_heart_filled else R.drawable.ic_heart_rounded
                    ),
                    contentDescription = "Favorite",
                    tint = Color.White
                )
            }
        }
        Text(
            text = recipe.title,
            fontFamily = InstrumentSerif,
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}