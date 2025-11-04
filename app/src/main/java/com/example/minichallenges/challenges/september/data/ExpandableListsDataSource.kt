package com.example.minichallenges.challenges.september.data

import com.example.minichallenges.challenges.september.Performer
import com.example.minichallenges.challenges.september.Stage
import com.example.minichallenges.challenges.september.theme.lime
import com.example.minichallenges.challenges.september.theme.orange
import com.example.minichallenges.challenges.september.theme.pink

val stages = listOf(
    Stage(
        "Stage A",
        lime,
        listOf(
            Performer("Morning Bloom", "11:00"),
            Performer("Synth River", "12:30")
        )
    ),
    Stage(
        "Stage B",
        orange,
        listOf(
            Performer("The Suntones", "13:00"),
            Performer("Blue Voltage", "14:15"),
            Performer("Midnight Echo", "15:30")
        )
    ),
    Stage(
        "Stage C",
        pink,
        listOf(
            Performer("Echo Machine", "16:00"),
            Performer("The 1975", "17:15")
        )
    )
)