package com.example.minichallenges.challenges.january.profileavatareditor.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.minichallenges.challenges.january.profileavatareditor.AvatarEditorScreen
import com.example.minichallenges.challenges.january.profileavatareditor.ProfileAvatarEditor

@Composable
fun AvatarEditorNavHost() {
    val backStack = rememberNavBackStack(ProfileAvatarKey)
    var isImageUpdatedFlag: Any? by remember { mutableStateOf(null) }
    NavDisplay(
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        entryProvider = entryProvider {

            entry<ProfileAvatarKey> {
                ProfileAvatarEditor(isImageUpdatedFlag) {
                    backStack.add(AvatarEditorKey(it.toString()))
                }
            }

            entry<AvatarEditorKey> { key ->
                AvatarEditorScreen(
                    uri = key.uri,
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                    onImageSaved = {
                        isImageUpdatedFlag = Any()
                        backStack.removeLastOrNull()
                    }
                )

            }

        }
    )
}
