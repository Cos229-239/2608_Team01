package com.team01.steamanalyst.service

import com.team01.steamanalyst.valuation.MarketplaceAnalyzer
import org.junit.Test

class CS2CapServiceTest {

    @Test
    fun testFetchPricesAndAnalyze() {

        val prices = CS2CapService()
            .fetchPrices("AWP | Asiimov (Field-Tested)")

        val analysis = MarketplaceAnalyzer()
            .analyze(prices)

        println("Providers returned: ${prices.size}")
        println("Usable providers: ${analysis.providerCount}")
        println("Cheapest: ${analysis.cheapestProvider} - $${analysis.cheapestPrice}")
        println("Highest: ${analysis.highestProvider} - $${analysis.highestPrice}")
        println("Average price: $${analysis.averagePrice}")
        println("Median price: $${analysis.medianPrice}")
        println("Price spread: $${analysis.priceSpread}")
        println(
            "Cheapest percent below median: " +
                    "${analysis.cheapestPercentBelowMedian}%"
        )

        assert(prices.isNotEmpty()) {
            "CS2Cap returned no marketplace prices"
        }

        assert(analysis.providerCount > 0) {
            "Marketplace analysis returned no usable prices"
        }

        assert(analysis.cheapestPrice != null) {
            "No cheapest marketplace price was calculated"
        }

        assert(analysis.medianPrice != null) {
            "No median marketplace price was calculated"
        }
    }
}