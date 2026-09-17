package com.team01.steamanalyst.data

data class MarketSummary(
    val bestProvider: String? = null,
    val bestPrice: Double? = null,
    val highestProvider: String? = null,
    val highestPrice: Double? = null,
    val averagePrice: Double? = null,
    val medianPrice: Double? = null,
    val priceSpread: Double? = null,
    val providerCount: Int = 0,
    val bestPricePercentBelowMedian: Double? = null,
    val dealRating: DealRating = DealRating.INSUFFICIENT_DATA
)