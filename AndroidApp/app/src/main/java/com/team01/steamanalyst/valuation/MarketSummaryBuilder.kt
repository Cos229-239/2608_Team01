package com.team01.steamanalyst.valuation

import com.team01.steamanalyst.data.MarketSummary
import com.team01.steamanalyst.data.MarketplaceAnalysis

class MarketSummaryBuilder {

    private val dealAnalyzer = DealAnalyzer()

    fun build(marketAnalysis: MarketplaceAnalysis): MarketSummary {

        val bestPrice = marketAnalysis.cheapestPrice

        if (
            bestPrice == null ||
            marketAnalysis.medianPrice == null ||
            marketAnalysis.providerCount == 0
        ) {
            return MarketSummary()
        }

        val dealAnalysis = dealAnalyzer.analyzeDeal(
            price = bestPrice,
            marketAnalysis = marketAnalysis
        )

        return MarketSummary(
            bestProvider = marketAnalysis.cheapestProvider,
            bestPrice = bestPrice,
            highestProvider = marketAnalysis.highestProvider,
            highestPrice = marketAnalysis.highestPrice,
            averagePrice = marketAnalysis.averagePrice,
            medianPrice = marketAnalysis.medianPrice,
            priceSpread = marketAnalysis.priceSpread,
            providerCount = marketAnalysis.providerCount,
            bestPricePercentBelowMedian =
                marketAnalysis.cheapestPercentBelowMedian,
            dealRating = dealAnalysis.rating
        )
    }
}