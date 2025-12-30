package com.example.minichallenges.challenges.august.parcelpigeonrace

data class ParcelPigeonRaceState(
    val status: Status = Status.CAN_RUN,
    val images: MutableList<PigeonImage> = (0..5).map {
        PigeonImage(it)
    }.toMutableList()
)

enum class Status {
    CAN_RUN,
    RUNNING,
    CAN_RUN_AGAIN
}