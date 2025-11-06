package com.example.minichallenges.challenges.september.data

import com.example.minichallenges.challenges.september.Genre
import com.example.minichallenges.challenges.september.Performance

val scheduleList = listOf(
    Performance(
        name = "Bon Iver",
        time = "15:30",
        stage = "Main Stage",
        genre = Genre.INDIE,
        description = "Atmospheric folk-electronic set to start the day."
    ),
    Performance(
        name = "Jamie xx",
        time = "16:00",
        stage = "Electro Stage",
        genre = Genre.ELECTRONIC,
        description = "A genre-bending solo set with deep bass and light textures."
    ),
    Performance(
        name = "Florence + The Machine",
        time = "17:00",
        stage = "Main Stage",
        genre = Genre.HEADLINER,
        description = "Special acoustic set this evening only."
    ),
    Performance(
        name = "The National",
        time = "18:00",
        stage = "Sunset Stage",
        genre = Genre.INDIE,
        description = "Known for their emotional rock anthems."
    ),
    Performance(
        name = "Björk",
        time = "18:30",
        stage = "Electro Stage",
        genre = Genre.EXPERIMENTAL,
        description = "Avant-garde visual and vocal performance."
    ),
    Performance(
        name = "Tame Impala",
        time = "19:00",
        stage = "Sunset Stage",
        genre = Genre.INDIE,
        description = "Celebrated psychedelic show from Australia."
    ),
    Performance(
        name = "The Chemical Brothers",
        time = "20:15",
        stage = "Electro Stage",
        genre = Genre.ELECTRONIC,
        description = "High-energy visuals with legendary electronica."
    ),
    Performance(
        name = "Foo Fighters",
        time = "21:00",
        stage = "Main Stage",
        genre = Genre.HEADLINER,
        description = "Classic stadium rock at its finest."
    ),
    Performance(
        name = "Arctic Monkeys",
        time = "22:00",
        stage = "Sunset Stage",
        genre = Genre.ALT_ROCK,
        description = "Charismatic blend of indie rock and post-punk revival."
    ),
    Performance(
        name = "Radiohead",
        time = "23:00",
        stage = "Main Stage",
        genre = Genre.HEADLINER,
        description = "Returning to the stage with a new album."
    )
)