package com.example.minichallenges.challenges.january.holidaymoviecollection.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "movie",
    indices = [Index(
        value = ["name"],
        unique = true
    )]
)
data class Movie constructor(
    @PrimaryKey(autoGenerate = true)
    val movieId: Long,
    val name: String,
    val imageRes: Int
)