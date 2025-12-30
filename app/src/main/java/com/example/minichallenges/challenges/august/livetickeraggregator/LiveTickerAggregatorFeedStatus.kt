package com.example.minichallenges.challenges.august.livetickeraggregator

import com.example.minichallenges.R

enum class LiveTickerAggregatorFeedStatus(val iconRes: Int) {
    UP_BACKGROUND(R.drawable.ic_feed_status_up_background),
    UP_NO_BACKGROUND(R.drawable.ic_feed_status_up_no_background),
    DOWN_BACKGROUND(R.drawable.ic_feed_status_down_background),
    DOWN_NO_BACKGROUND(R.drawable.ic_feed_status_down_no_background),
    EQUAL(R.drawable.ic_feed_status_equal),
    BROKEN(R.drawable.ic_feed_status_broken),
}