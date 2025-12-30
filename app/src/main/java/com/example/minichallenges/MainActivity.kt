package com.example.minichallenges

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.minichallenges.challenges.august.orderqueueoutpost.OrderQueueOutpostRoot
import com.example.minichallenges.challenges.august.thermometer.ThermometerTrek
import com.example.minichallenges.challenges.august.thermometer.ThermometerTrekRoot
import com.example.minichallenges.challenges.september.AccessibleAudioSchedule
import com.example.minichallenges.challenges.september.MultiStageTimelinePainter

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OrderQueueOutpostRoot()
        }
    }
}
