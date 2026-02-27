package com.example.minichallenges

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.minichallenges.challenges.january.freshstartsettings.FreshStartSettings
import com.example.minichallenges.challenges.january.holidaymoviecollection.nav.HolidayMovieNavHost
import com.example.minichallenges.challenges.january.januaryrecipes.JanuaryRecipeRefresh
import com.example.minichallenges.challenges.january.profileavatareditor.AvatarEditorScreen
import com.example.minichallenges.challenges.january.profileavatareditor.ProfileAvatarEditor
import com.example.minichallenges.challenges.january.profileavatareditor.nav.AvatarEditorNavHost
import com.example.minichallenges.challenges.january.theme.HolidayMovieCollectionTheme
import com.example.minichallenges.challenges.january.theme.JanuaryTheme
import com.example.minichallenges.challenges.january.wintertravelgallery.JanuaryNavHost

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            JanuaryTheme {
                JanuaryNavHost()
            }
        }
    }
}
