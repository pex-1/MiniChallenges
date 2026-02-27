package com.example.minichallenges.challenges.january.wintertravelgallery

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import com.example.minichallenges.challenges.january.state.Destination

@Serializable
object GalleryKey : NavKey

@Serializable
data class DestinationKey(val destination: Destination) : NavKey