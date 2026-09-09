package com.team01.steamanalyst.service

import org.junit.Test

class CS2CapServiceTest {

    @Test
    fun testFetchPrices() {
        val prices = CS2CapService()
            .fetchPrices("AK-47 | Redline (Field-Tested)")

        println("Marketplaces returned: ${prices.size}")

        prices.forEach { price ->
            println(
                "${price.provider}: " +
                        "$${price.lowestAsk} | " +
                        "Quantity: ${price.quantity} | " +
                        "Updated: ${price.lastUpdated}"
            )
        }

        assert(prices.isNotEmpty()) {
            "CS2Cap returned no marketplace prices"
        }
    }
}