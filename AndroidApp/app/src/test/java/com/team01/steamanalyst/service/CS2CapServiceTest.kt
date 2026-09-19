package com.team01.steamanalyst.service

import com.team01.steamanalyst.valuation.MarketplaceAnalyzer
import com.team01.steamanalyst.valuation.MarketplaceRankingAnalyzer
import com.team01.steamanalyst.valuation.MarketSummaryBuilder
import org.junit.Test

class CS2CapServiceTest {

    @Test
    fun testFetchPricesAndAnalyze() {

        val marketHashName = "AWP | Asiimov (Field-Tested)"

        val prices = CS2CapService()
            .fetchPrices(marketHashName)

        val analysis = MarketplaceAnalyzer()
            .analyze(prices)

        val summary = MarketSummaryBuilder()
            .build(analysis)

        val rankings = MarketplaceRankingAnalyzer()
            .rank(analysis)

        println("Item: $marketHashName")
        println()
        println("Providers returned: ${prices.size}")
        println("Usable providers: ${analysis.providerCount}")
        println()
        println("Best provider: ${summary.bestProvider}")
        println("Best price: $${summary.bestPrice}")
        println("Highest provider: ${summary.highestProvider}")
        println("Highest price: $${summary.highestPrice}")
        println("Average price: $${summary.averagePrice}")
        println("Median price: $${summary.medianPrice}")
        println("Price spread: $${summary.priceSpread}")
        println(
            "Best price percent below median: " +
                    "${summary.bestPricePercentBelowMedian}%"
        )
        println("Deal rating: ${summary.dealRating}")

        println()
        println("Marketplace Rankings:")

        rankings.forEach { ranking ->
            println(
                "#${ranking.rank} ${ranking.provider} - " +
                        "$${ranking.price} | " +
                        "${ranking.percentDifferenceFromMedian}% vs median | " +
                        "${ranking.percentAboveCheapest}% above cheapest"
            )
        }

        assert(prices.isNotEmpty()) {
            "CS2Cap returned no marketplace prices"
        }

        assert(analysis.providerCount > 0) {
            "Marketplace analysis returned no usable prices"
        }

        assert(summary.bestPrice != null) {
            "Market summary did not contain a best price"
        }

        assert(summary.medianPrice != null) {
            "Market summary did not contain a median price"
        }

        assert(summary.providerCount > 0) {
            "Market summary did not contain usable providers"
        }

        assert(rankings.isNotEmpty()) {
            "Marketplace ranking returned no results"
        }

        assert(rankings.size == analysis.providerCount) {
            "Marketplace ranking count did not match usable provider count"
        }
    }
}