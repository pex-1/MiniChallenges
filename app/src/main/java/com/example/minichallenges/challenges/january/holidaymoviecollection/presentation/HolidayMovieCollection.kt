package com.example.minichallenges.challenges.january.holidaymoviecollection.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minichallenges.R
import com.example.minichallenges.challenges.january.holidaymoviecollection.data.entity.CollectionWithMovie
import com.example.minichallenges.challenges.january.theme.HolidayMovieCollectionTheme
import com.example.minichallenges.challenges.january.theme.JakartaSans
import com.example.minichallenges.challenges.january.theme.textPlaceholder
import com.example.minichallenges.challenges.january.holidaymoviecollection.data.entity.Collection

@Preview
@Composable
private fun HolidayMovieCollectionPreview() {
    HolidayMovieCollectionTheme {
        HolidayMovieCollection()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HolidayMovieCollection(
    openAddCollectionScreen: () -> Unit = {},
    openCollectionDetailScreen: (CollectionWithMovie) -> Unit = {}
) {
    val viewModel: HolidayMovieCollectionViewModel = viewModel()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.onIntent(
            MovieCollectionIntent.FetchInitialData(
                context = context.applicationContext
            )
        )
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            LargeTopAppBar(title = {
                Text(
                    text = "Holiday Movie \nCollection \uD83C\uDF84",
                    fontFamily = JakartaSans,
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
                )
            }, colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color.Transparent))
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = openAddCollectionScreen,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )

            if (uiState.collections.isEmpty()) {
                EmptyCollectionText(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.collections) { collection ->
                        CollectionItem(
                            collectionWithMovie = collection,
                            onClick = {
                                openCollectionDetailScreen(collection)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyCollectionText(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "No movie bundles yet",
            fontFamily = JakartaSans,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Create your first holiday collection",
            fontFamily = JakartaSans,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@Composable
private fun CollectionItemPreview() {
    HolidayMovieCollectionTheme {
        val data = CollectionWithMovie(
            collection = Collection(
                collectionId = 1,
                name = "Christmas Movies"
            ),
            movies = listOf()
        )
        CollectionItem(data)
    }
}

@Composable
fun CollectionItem(
    collectionWithMovie: CollectionWithMovie,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .clip(MaterialTheme.shapes.small)
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(R.drawable.ic_folder_icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.width(56.dp)
            )

            Text(
                text = collectionWithMovie.collection.name[0].uppercase(),
                fontFamily = JakartaSans,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.textPlaceholder,
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1F)) {
            Text(
                text = collectionWithMovie.collection.name,
                fontFamily = JakartaSans,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${collectionWithMovie.movies.size} movies",
                fontFamily = JakartaSans,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

}