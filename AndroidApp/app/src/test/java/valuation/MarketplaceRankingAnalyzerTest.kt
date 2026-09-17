package com.team01.steamanalyst.valuation

import com.team01.steamanalyst.data.MarketplaceAnalysis
import com.team01.steamanalyst.data.MarketplacePrice
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MarketplaceRankingAnalyzerTest {

    @Test
    fun ranksMarketplacesFromCheapestToHighest() {

        val analysis = MarketplaceAnalysis(
            validPrices = listOf(
                MarketplacePrice(
                    provider = "steam",
                    lowestAsk = 120.0,
                    quantity = 10,
                    listingUrl = null,
                    lastUpdated = null
                ),
                MarketplacePrice(
                    provider = "csfloat",
                    lowestAsk = 80.0,
                    quantity = 10,
                    listingUrl = null,
                    lastUpdated = null
                ),
                MarketplacePrice(
                    provider = "skinport",
                    lowestAsk = 100.0,
                    quantity = 10,
                    listingUrl = null,
                    lastUpdated = null
                )
            ),
            cheapestProvider = "csfloat",
            cheapestPrice = 80.0,
            highestProvider = "steam",
            highestPrice = 120.0,
            averagePrice = 100.0,
            medianPrice = 100.0,
            priceSpread = 40.0,
            cheapestPercentBelowMedian = 20.0,
            providerCount = 3
        )

        val rankings = MarketplaceRankingAnalyzer()
            .rank(analysis)

        assertEquals(3, rankings.size)

        assertEquals(1, rankings[0].rank)
        assertEquals("csfloat", rankings[0].provider)
        assertEquals(80.0, rankings[0].price, 0.001)

        assertEquals(2, rankings[1].rank)
        assertEquals("skinport", rankings[1].provider)
        assertEquals(100.0, rankings[1].price, 0.001)

        assertEquals(3, rankings[2].rank)
        assertEquals("steam", rankings[2].provider)
        assertEquals(120.0, rankings[2].price, 0.001)

        assertEquals(
            -20.0,
            rankings[0].percentDifferenceFromMedian,
            0.001
        )

        assertEquals(
            0.0,
            rankings[1].percentDifferenceFromMedian,
            0.001
        )

        assertEquals(
            20.0,
            rankings[2].percentDifferenceFromMedian,
            0.001
        )

        assertEquals(
            0.0,
            rankings[0].percentAboveCheapest,
            0.001
        )

        assertEquals(
            25.0,
            rankings[1].percentAboveCheapest,
            0.001
        )

        assertEquals(
            50.0,
            rankings[2].percentAboveCheapest,
            0.001
        )

        assertTrue(
            rankings.zipWithNext().all {
                it.first.price <= it.second.price
            }
        )
    }

    @Test
    fun emptyAnalysisReturnsEmptyRanking() {

        val rankings = MarketplaceRankingAnalyzer()
            .rank(MarketplaceAnalysis())

        assertTrue(rankings.isEmpty())
    }
}