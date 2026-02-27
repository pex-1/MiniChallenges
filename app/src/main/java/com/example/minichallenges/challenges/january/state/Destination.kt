package com.example.minichallenges.challenges.january.state

import com.example.minichallenges.R

enum class Destination(
    val title: String,
    val imageRes: Int,
    val imageUrls: List<String>
) {
    ALPS(
        "Alps", R.drawable.alps, listOf(
            "https://images.unsplash.com/photo-1506905925346-21bda4d2df4?w=600&q=80",
            "https://images.unsplash.com/photo-1519904981063-b0cf448d479e?w=600&q=80",
            "https://images.unsplash.com/photo-1551632811-561732d1e306?w=600&q=80",
            "https://images.unsplash.com/photo-1486870591958-9b9d0d1dda99?w=600&q=80",
            "https://images.unsplash.com/photo-1544735716-392fe2489ffa?w=600&q=80",
            "https://images.unsplash.com/photo-1605540436563-5bca99ae766?w=600&q=80"
        )
    ),
    LAPLAND(
        "Lapland", R.drawable.lapland, listOf(
            "https://images.unsplash.com/photo-1483347756197-71ef80e95f73?w=600&q=80",
            "https://images.unsplash.com/photo-1478131143081-80f7f84ca84d?w=600&q=80",
            "https://images.unsplash.com/photo-1513121245545-92e3495e2f07?w=600&q=80",
            "https://images.unsplash.com/photo-1579033461380-adb47c3eb938?w=600&q=80",
            "https://images.unsplash.com/photo-1551632811-561732d1e306?w=600&q=80",
            "https://images.unsplash.com/photo-1546552768-6d7c2c0ab0e8?w=600&q=80"
        )
    ),
    NORWAY(
        "Norway Fjords", R.drawable.norway_fjords, listOf(
            "https://images.unsplash.com/photo-1464037866556-6812c9d1c72e?w=600&q=80",
            "https://images.unsplash.com/photo-1531366936337-7c912a4589a7?w=600&q=80",
            "https://images.unsplash.com/photo-1509023464722-18d996393ca8?w=600&q=80",
            "https://images.unsplash.com/photo-1517760444937-f6397edcbbcd?w=600&q=80",
            "https://images.unsplash.com/photo-1552033092-3e9f0b11e120?w=600&q=80",
            "https://images.unsplash.com/photo-1544551763-46a013bb7d5?w=600&q=80"
        )
    ),
    ICELAND(
        "Iceland", R.drawable.iceland, listOf(
            "https://images.unsplash.com/photo-1504893524553-b855bce32c67?w=600&q=80",
            "https://images.unsplash.com/photo-1519567241046-7f570eee3ce?w=600&q=80",
            "https://images.unsplash.com/photo-1474690455603-a369ec1293f?w=600&q=80",
            "https://images.unsplash.com/photo-1551632811-561732d1e306?w=600&q=80",
            "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=600&q=80",
            "https://images.unsplash.com/photo-1531366936337-7c912a4589a7?w=600&q=80"
        )
    ),
    SWISS(
        "Swiss Villages", R.drawable.swiss_villages, listOf(
            "https://images.unsplash.com/photo-1527004013197-933c4bb611b3?w=600&q=80",
            "https://images.unsplash.com/photo-1548678967-f1aec58f6fb2?w=600&q=80",
            "https://images.unsplash.com/photo-1572120360610-d971b9d776c?w=600&q=80",
            "https://images.unsplash.com/photo-1533587851505-d119e13fa0d7?w=600&q=80",
            "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=600&q=80",
            "https://images.unsplash.com/photo-1506905925346-21bda4d32d4?w=600&q=80"
        )
    ),
    CANADA(
        "Canadian Rockies", R.drawable.canadian_rockies, listOf(
            "https://images.unsplash.com/photo-1503614472-8c93d56e92ce?w=600&q=80",
            "https://images.unsplash.com/photo-1481627834876-b7833e8f557?w=600&q=80",
            "https://images.unsplash.com/photo-1476514525535-07fb3b4ae5f1?w=600&q=80",
            "https://images.unsplash.com/photo-1485808191679-5f86510681a?w=600&q=80",
            "https://images.unsplash.com/photo-1519904981063-b0cf448d479e?w=600&q=80",
            "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=600&q=80"
        )
    )
}