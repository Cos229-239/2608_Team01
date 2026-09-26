package com.team01.steamanalyst.data

data class TopMover(
    val marketHashName: String,
    val currentPrice: Double,
    val previousPrice: Double,
    val percentChange: Double
)
