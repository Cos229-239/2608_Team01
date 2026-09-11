package com.team01.steamanalyst.valuation
import com.team01.steamanalyst.data.MarketplaceAnalysis
import com.team01.steamanalyst.data.MarketplacePrice

class MarketplaceAnalyzer {
    private val trustedProviders = setOf(
        "steam",
        "csfloat",
        "skinport",
        "buff163",
        "dmarket",
        "skinbaron",
        "tradeit",
        "lisskins"
    )
    fun analyze(prices: List<MarketplacePrice>): MarketplaceAnalysis {

        val usablePrices = prices
            .filter { it.provider.lowercase() in trustedProviders }
            .filter { it.lowestAsk != null }
            .filter { it.lowestAsk!! > 0.0 }
            .filter { it.quantity == null || it.quantity > 0 }

        if (usablePrices.isEmpty()) {
            return MarketplaceAnalysis()
        }

        val initialSorted = usablePrices.sortedBy { it.lowestAsk }
        val initialValues = initialSorted.mapNotNull { it.lowestAsk }

        val initialMedian = if (initialValues.size % 2 == 0) {
            val middle = initialValues.size / 2
            (initialValues[middle - 1] + initialValues[middle]) / 2.0
        } else {
            initialValues[initialValues.size / 2]
        }

        val minimumAllowed = initialMedian * 0.5
        val maximumAllowed = initialMedian * 1.5

        val filteredPrices = initialSorted.filter {
            val price = it.lowestAsk ?: return@filter false
            price in minimumAllowed..maximumAllowed
        }

        if (filteredPrices.isEmpty()) {
            return MarketplaceAnalysis()
        }

        val sortedPrices = filteredPrices.sortedBy { it.lowestAsk }
        val priceValues = sortedPrices.mapNotNull { it.lowestAsk }

        val cheapest = sortedPrices.first()
        val highest = sortedPrices.last()

        val averagePrice = priceValues.average()

        val medianPrice = if (priceValues.size % 2 == 0) {
            val middle = priceValues.size / 2
            (priceValues[middle - 1] + priceValues[middle]) / 2.0
        } else {
            priceValues[priceValues.size / 2]
        }

        val spread = highest.lowestAsk!! - cheapest.lowestAsk!!

        val cheapestPercentBelowMedian =
            if (medianPrice > 0.0) {
                ((medianPrice - cheapest.lowestAsk!!) / medianPrice) * 100.0
            } else {
                null
            }

        return MarketplaceAnalysis(
            validPrices = sortedPrices,
            cheapestProvider = cheapest.provider,
            cheapestPrice = cheapest.lowestAsk,
            highestProvider = highest.provider,
            highestPrice = highest.lowestAsk,
            averagePrice = averagePrice,
            medianPrice = medianPrice,
            priceSpread = spread,
            cheapestPercentBelowMedian = cheapestPercentBelowMedian,
            providerCount = sortedPrices.size
        )
    }
}