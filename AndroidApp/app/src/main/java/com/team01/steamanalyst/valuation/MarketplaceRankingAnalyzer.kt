package com.team01.steamanalyst.valuation

import com.team01.steamanalyst.data.MarketplaceAnalysis
import com.team01.steamanalyst.data.MarketplaceRanking

class MarketplaceRankingAnalyzer {

    fun rank(
        marketAnalysis: MarketplaceAnalysis
    ): List<MarketplaceRanking> {

        val median = marketAnalysis.medianPrice
            ?: return emptyList()

        val cheapest = marketAnalysis.cheapestPrice
            ?: return emptyList()

        if (
            median <= 0.0 ||
            cheapest <= 0.0 ||
            marketAnalysis.validPrices.isEmpty()
        ) {
            return emptyList()
        }

        return marketAnalysis.validPrices
            .mapNotNull { marketplace ->

                val price = marketplace.lowestAsk
                    ?: return@mapNotNull null

                val differenceFromMedian =
                    ((price - median) / median) * 100.0

                val percentAboveCheapest =
                    ((price - cheapest) / cheapest) * 100.0

                Triple(
                    marketplace.provider,
                    price,
                    Pair(
                        differenceFromMedian,
                        percentAboveCheapest
                    )
                )
            }
            .sortedBy { it.second }
            .mapIndexed { index, result ->

                MarketplaceRanking(
                    rank = index + 1,
                    provider = result.first,
                    price = result.second,
                    percentDifferenceFromMedian =
                        result.third.first,
                    percentAboveCheapest =
                        result.third.second
                )
            }
    }
}