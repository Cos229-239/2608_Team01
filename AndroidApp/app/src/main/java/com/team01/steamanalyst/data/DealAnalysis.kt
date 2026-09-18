package com.team01.steamanalyst.data

data class DealAnalysis(
    val rating: DealRating = DealRating.INSUFFICIENT_DATA,
    val bestProvider: String? = null,
    val bestPrice: Double? = null,
    val marketMedian: Double? = null,
    val percentDifferenceFromMedian: Double? = null,
    val providerCount: Int = 0
)