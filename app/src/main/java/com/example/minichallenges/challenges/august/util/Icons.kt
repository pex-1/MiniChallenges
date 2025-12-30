package com.example.minichallenges.challenges.august.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.minichallenges.R

val ThermometerIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.thermometer)

val CheckIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.check_circle)

val PlayIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.play)

val PauseIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.pause_circle)