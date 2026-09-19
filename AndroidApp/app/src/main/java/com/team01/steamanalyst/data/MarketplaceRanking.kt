package com.team01.steamanalyst.data

data class MarketplaceRanking(
    val rank: Int,
    val provider: String,
    val price: Double,
    val percentDifferenceFromMedian: Double,
    val percentAboveCheapest: Double
)