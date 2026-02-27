package com.example.minichallenges.challenges.january.holidaymoviecollection.nav

import androidx.navigation3.runtime.NavKey
import com.example.minichallenges.challenges.january.holidaymoviecollection.data.entity.CollectionWithMovie
import kotlinx.serialization.Serializable

@Serializable
object CollectionsKey : NavKey

@Serializable
object AddCollectionKey : NavKey

@Serializable
data class CollectionDetailKey(val collectionId: CollectionWithMovie) : NavKey