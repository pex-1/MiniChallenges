package com.example.minichallenges.challenges.august.orderqueueoutpost

data class OrderQueueOutpostState(
    val producerStarted: Boolean = false,
    val consumerRunning: Boolean = false,
    val orders: Int = 0,
    val status: Status = Status.START
)

enum class Status {
    START,
    PAUSED,
    PROCESSING
}