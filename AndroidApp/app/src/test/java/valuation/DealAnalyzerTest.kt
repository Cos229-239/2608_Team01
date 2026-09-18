package com.team01.steamanalyst.valuation

import com.team01.steamanalyst.data.DealRating
import com.team01.steamanalyst.data.MarketplaceAnalysis
import org.junit.Assert.assertEquals
import org.junit.Test

class DealAnalyzerTest {

    private val analyzer = DealAnalyzer()

    private fun createMarketAnalysis(): MarketplaceAnalysis {
        return MarketplaceAnalysis(
            cheapestProvider = "csfloat",
            cheapestPrice = 90.0,
            medianPrice = 100.0,
            providerCount = 8
        )
    }

    @Test
    fun goodDealWhenPriceIsWellBelowMedian() {

        val result = analyzer.analyzeDeal(
            price = 80.0,
            marketAnalysis = createMarketAnalysis()
        )

        assertEquals(DealRating.GOOD_DEAL, result.rating)
        assertEquals(-20.0, result.percentDifferenceFromMedian!!, 0.001)
    }

    @Test
    fun fairPriceWhenPriceIsNearMedian() {

        val result = analyzer.analyzeDeal(
            price = 95.0,
            marketAnalysis = createMarketAnalysis()
        )

        assertEquals(DealRating.FAIR_PRICE, result.rating)
        assertEquals(-5.0, result.percentDifferenceFromMedian!!, 0.001)
    }

    @Test
    fun aboveMarketWhenPriceIsWellAboveMedian() {

        val result = analyzer.analyzeDeal(
            price = 115.0,
            marketAnalysis = createMarketAnalysis()
        )

        assertEquals(DealRating.ABOVE_MARKET, result.rating)
        assertEquals(15.0, result.percentDifferenceFromMedian!!, 0.001)
    }

    @Test
    fun insufficientDataWhenMarketAnalysisIsEmpty() {

        val result = analyzer.analyzeDeal(
            price = 100.0,
            marketAnalysis = MarketplaceAnalysis()
        )

        assertEquals(DealRating.INSUFFICIENT_DATA, result.rating)
    }
}