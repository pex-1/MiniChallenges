package com.example.minichallenges.challenges.september.data

import com.example.minichallenges.challenges.september.MusicGenre
import com.example.minichallenges.challenges.september.Session

val festivalSchedule = listOf(
    Session(
        artistName = "DJ A",
        genre = MusicGenre.Electro,
        startTime = Pair(12, 0),
        endTime = Pair(13, 0),
        color = MusicGenre.Electro.color
    ),
    Session(
        artistName = "Band X",
        genre = MusicGenre.Main,
        startTime = Pair(13, 0),
        endTime = Pair(14, 30),
        color = MusicGenre.Main.color
    ),
    Session(
        artistName = "RockZ",
        genre = MusicGenre.Rock,
        startTime = Pair(14, 0),
        endTime = Pair(15, 0),
        color = MusicGenre.Rock.color
    ),
    Session(
        artistName = "Ambient Line",
        genre = MusicGenre.Electro,
        startTime = Pair(15, 0),
        endTime = Pair(16, 30),
        color = MusicGenre.Electro.color
    ),
    Session(
        artistName = "Florence + The Machine",
        genre = MusicGenre.Main,
        startTime = Pair(16, 30),
        endTime = Pair(18, 0),
        color = MusicGenre.Main.color
    ),
    Session(
        artistName = "The National",
        genre = MusicGenre.Rock,
        startTime = Pair(17, 0),
        endTime = Pair(18, 0),
        color = MusicGenre.Rock.color
    ),
    Session(
        artistName = "Jamie xx",
        genre = MusicGenre.Electro,
        startTime = Pair(18, 0),
        endTime = Pair(19, 0),
        color = MusicGenre.Electro.color
    ),
    Session(
        artistName = "Tame Impala",
        genre = MusicGenre.Main,
        startTime = Pair(19, 0),
        endTime = Pair(20, 30),
        color = MusicGenre.Main.color
    ),
    Session(
        artistName = "Arctic Monkeys",
        genre = MusicGenre.Rock,
        startTime = Pair(20, 0),
        endTime = Pair(21, 30),
        color = MusicGenre.Rock.color
    ),
    Session(
        artistName = "Radiohead",
        genre = MusicGenre.Main,
        startTime = Pair(21, 30),
        endTime = Pair(23, 0),
        color = MusicGenre.Main.color
    )
)