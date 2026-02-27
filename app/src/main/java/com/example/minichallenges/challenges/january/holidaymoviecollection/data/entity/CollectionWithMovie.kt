package com.example.minichallenges.challenges.january.holidaymoviecollection.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import kotlinx.serialization.Serializable

@Entity(
    tableName = "playlist_song_cross_ref",
    primaryKeys = ["collectionId", "movieId"]
)
data class CollectionWithMovieCrossRef(
    val collectionId: Long,
    val movieId: Long
)

@Serializable
data class CollectionWithMovie constructor(
    @Embedded
    val collection: Collection,
    @Relation(
        parentColumn = "collectionId",
        entityColumn = "movieId",
        associateBy = Junction(CollectionWithMovieCrossRef::class)
    )
    val movies: List<Movie>
)