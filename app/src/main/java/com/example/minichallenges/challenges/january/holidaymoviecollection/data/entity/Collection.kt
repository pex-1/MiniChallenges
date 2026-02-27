package com.example.minichallenges.challenges.january.holidaymoviecollection.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "collections"
)
data class Collection constructor(
    @PrimaryKey(autoGenerate = true)
    val collectionId: Long,
    val name: String
)