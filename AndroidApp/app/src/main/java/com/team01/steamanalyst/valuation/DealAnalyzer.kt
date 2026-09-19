package com.team01.steamanalyst.valuation

import com.team01.steamanalyst.data.DealAnalysis
import com.team01.steamanalyst.data.DealRating
import com.team01.steamanalyst.data.MarketplaceAnalysis

class DealAnalyzer {

    fun analyzeDeal(
        price: Double,
        marketAnalysis: MarketplaceAnalysis
    ): DealAnalysis {

        val median = marketAnalysis.medianPrice

        if (
            price <= 0.0 ||
            median == null ||
            median <= 0.0 ||
            marketAnalysis.providerCount == 0
        ) {
            return DealAnalysis()
        }

        val percentDifference =
            ((price - median) / median) * 100.0

        val rating = when {
            percentDifference <= -10.0 ->
                DealRating.GOOD_DEAL

            percentDifference >= 10.0 ->
                DealRating.ABOVE_MARKET

            else ->
                DealRating.FAIR_PRICE
        }

        return DealAnalysis(
            rating = rating,
            bestProvider = marketAnalysis.cheapestProvider,
            bestPrice = marketAnalysis.cheapestPrice,
            marketMedian = median,
            percentDifferenceFromMedian = percentDifference,
            providerCount = marketAnalysis.providerCount
        )
    }
}