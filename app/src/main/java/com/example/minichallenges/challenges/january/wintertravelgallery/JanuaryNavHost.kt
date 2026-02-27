package com.example.minichallenges.challenges.january.wintertravelgallery

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun JanuaryNavHost() {
    val backStack = rememberNavBackStack(GalleryKey)

    BackHandler(enabled = backStack.size > 1) {
        backStack.removeLast()
    }

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<GalleryKey> {
                WinterTravelGallery(
                    openDestination = { destination ->
                        backStack.add(DestinationKey(destination))
                    }
                )
            }

            entry<DestinationKey> { key ->
                DestinationDetailsScreen(
                    destination = key.destination,
                    onBack = { backStack.removeLast() }
                )
            }
        }
    )
}