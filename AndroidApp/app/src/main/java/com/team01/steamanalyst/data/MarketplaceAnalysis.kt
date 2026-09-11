package com.team01.steamanalyst.data

data class MarketplaceAnalysis(
    val validPrices: List<MarketplacePrice> = emptyList(),
    val cheapestProvider: String? = null,
    val cheapestPrice: Double? = null,
    val highestProvider: String? = null,
    val highestPrice: Double? = null,
    val averagePrice: Double? = null,
    val medianPrice: Double? = null,
    val priceSpread: Double? = null,
    val cheapestPercentBelowMedian: Double? = null,
    val providerCount: Int = 0
)