package com.example.minichallenges.challenges.january.profileavatareditor.nav

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class AvatarEditorKey(val uri: String) : NavKey

@Serializable
object ProfileAvatarKey: NavKey