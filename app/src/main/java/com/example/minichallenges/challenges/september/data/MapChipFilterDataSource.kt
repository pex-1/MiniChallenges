package com.example.minichallenges.challenges.september.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Wc
import com.example.minichallenges.R
import com.example.minichallenges.challenges.september.MapChip
import com.example.minichallenges.challenges.september.MarkerType
import com.example.minichallenges.challenges.september.theme.lime
import com.example.minichallenges.challenges.september.theme.orange
import com.example.minichallenges.challenges.september.theme.pink

val filters = listOf(
    MapChip("Stages", lime, MarkerType.STAGE, R.drawable.stages, Icons.Default.MusicNote),
    MapChip("Food", pink, MarkerType.FOOD, R.drawable.food,Icons.Default.Restaurant),
    MapChip("WC", orange, MarkerType.WC, R.drawable.wc,Icons.Default.Wc)
)