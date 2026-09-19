package com.team01.steamanalyst.valuation

import com.team01.steamanalyst.data.DealRating
import com.team01.steamanalyst.data.MarketplaceAnalysis
import org.junit.Assert.assertEquals
import org.junit.Test

class MarketSummaryBuilderTest {

    private val builder = MarketSummaryBuilder()

    @Test
    fun buildsSummaryFromMarketplaceAnalysis() {

        val marketAnalysis = MarketplaceAnalysis(
            cheapestProvider = "csfloat",
            cheapestPrice = 80.0,
            highestProvider = "steam",
            highestPrice = 120.0,
            averagePrice = 100.0,
            medianPrice = 100.0,
            priceSpread = 40.0,
            cheapestPercentBelowMedian = 20.0,
            providerCount = 8
        )

        val summary = builder.build(marketAnalysis)

        assertEquals("csfloat", summary.bestProvider)
        assertEquals(80.0, summary.bestPrice!!, 0.001)

        assertEquals("steam", summary.highestProvider)
        assertEquals(120.0, summary.highestPrice!!, 0.001)

        assertEquals(100.0, summary.averagePrice!!, 0.001)
        assertEquals(100.0, summary.medianPrice!!, 0.001)

        assertEquals(40.0, summary.priceSpread!!, 0.001)
        assertEquals(20.0, summary.bestPricePercentBelowMedian!!, 0.001)

        assertEquals(8, summary.providerCount)
        assertEquals(DealRating.GOOD_DEAL, summary.dealRating)
    }

    @Test
    fun returnsEmptySummaryWhenMarketplaceDataIsMissing() {

        val summary = builder.build(
            MarketplaceAnalysis()
        )

        assertEquals(null, summary.bestProvider)
        assertEquals(null, summary.bestPrice)
        assertEquals(0, summary.providerCount)
        assertEquals(
            DealRating.INSUFFICIENT_DATA,
            summary.dealRating
        )
    }
}