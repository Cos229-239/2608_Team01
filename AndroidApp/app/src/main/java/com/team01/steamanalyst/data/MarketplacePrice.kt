package com.team01.steamanalyst.data

data class MarketplacePrice(
    val provider: String,
    val lowestAsk: Double?,
    val quantity: Int?,
    val listingUrl: String?,
    val lastUpdated: String?
)