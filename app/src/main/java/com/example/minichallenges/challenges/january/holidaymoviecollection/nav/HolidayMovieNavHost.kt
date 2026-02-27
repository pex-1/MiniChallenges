package com.example.minichallenges.challenges.january.holidaymoviecollection.nav

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.minichallenges.challenges.january.holidaymoviecollection.presentation.CollectionDetailScreen
import com.example.minichallenges.challenges.january.holidaymoviecollection.presentation.CreateCollectionScreen
import com.example.minichallenges.challenges.january.holidaymoviecollection.presentation.HolidayMovieCollection

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun HolidayMovieNavHost() {
    val backStack = rememberNavBackStack(CollectionsKey)

    BackHandler(enabled = backStack.size > 1) {
        backStack.removeLast()
    }

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {

            entry<CollectionsKey> {
                HolidayMovieCollection(
                    openAddCollectionScreen = { backStack.add(AddCollectionKey) },
                    openCollectionDetailScreen = { cwm ->
                        backStack.add(CollectionDetailKey(cwm))
                    }
                )
            }

            entry<AddCollectionKey> {
                CreateCollectionScreen(onBack = { backStack.removeLast() })
            }

            entry<CollectionDetailKey> { key ->
                CollectionDetailScreen(
                    collectionWithMovie = key.collectionId,
                    onBack = { backStack.removeLast() }
                )
            }
        }
    )

}