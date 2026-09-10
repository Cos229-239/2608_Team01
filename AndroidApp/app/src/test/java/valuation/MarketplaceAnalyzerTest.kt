package com.team01.steamanalyst.valuation

import com.team01.steamanalyst.data.MarketplacePrice
import org.junit.Test

class MarketplaceAnalyzerTest {
    // Live integration test.
    // Requires internet access and a valid CS2Cap API key.
    // Run manually to verify CS2Cap API integration.
    @Test
    fun liveTestFetchPricesAndAnalyze() {

        val prices = listOf(
            MarketplacePrice(
                provider = "csfloat",
                lowestAsk = 26.00,
                quantity = 100,
                listingUrl = null,
                lastUpdated = null
            ),
            MarketplacePrice(
                provider = "skinport",
                lowestAsk = 29.12,
                quantity = 100,
                listingUrl = null,
                lastUpdated = null
            ),
            MarketplacePrice(
                provider = "steam",
                lowestAsk = 37.97,
                quantity = 100,
                listingUrl = null,
                lastUpdated = null
            ),
            MarketplacePrice(
                provider = "dmarket",
                lowestAsk = 27.59,
                quantity = 100,
                listingUrl = null,
                lastUpdated = null
            ),
            MarketplacePrice(
                provider = "buff163",
                lowestAsk = 27.19,
                quantity = 100,
                listingUrl = null,
                lastUpdated = null
            ),
            MarketplacePrice(
                provider = "randommarket",
                lowestAsk = 1.00,
                quantity = 100,
                listingUrl = null,
                lastUpdated = null
            )
        )

        val analysis = MarketplaceAnalyzer().analyze(prices)

        println("Trusted providers used: ${analysis.providerCount}")
        println("Cheapest: ${analysis.cheapestProvider} - $${analysis.cheapestPrice}")
        println("Median: $${analysis.medianPrice}")
        println(
            "Cheapest percent below median: " +
                    "${analysis.cheapestPercentBelowMedian}%"
        )

        assert(analysis.providerCount == 5) {
            "Untrusted providers were not filtered correctly"
        }

        assert(analysis.cheapestProvider == "csfloat") {
            "Cheapest trusted provider was incorrect"
        }

        assert(analysis.cheapestPrice == 26.00) {
            "Cheapest trusted price was incorrect"
        }

        assert(analysis.medianPrice != null) {
            "Median price was not calculated"
        }

        assert(analysis.cheapestPercentBelowMedian != null) {
            "Percent below median was not calculated"
        }
    }
}