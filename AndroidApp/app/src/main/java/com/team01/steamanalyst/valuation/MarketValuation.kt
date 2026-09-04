package com.team01.steamanalyst.valuation
import com.team01.steamanalyst.data.InventoryValuation
import com.team01.steamanalyst.data.SkinPortItem
import com.team01.steamanalyst.data.SteamInventoryItem
import com.team01.steamanalyst.data.ValuedInventoryItem

class MarketValuation {

    fun calculate(
        inventory: List<SteamInventoryItem>,
        marketItems: List<SkinPortItem>
    ): InventoryValuation {

        val marketMap = marketItems.associateBy {
            it.marketHashName
        }

        val valuedItems = mutableListOf<ValuedInventoryItem>()

        var matchedItems = 0
        var unmatchedItems = 0
        var totalEstimatedValue = 0.0

        for (steamItem in inventory) {

            val marketItem = marketMap[steamItem.marketHashName]

            if (marketItem != null) {

                matchedItems++

                val itemValue =
                    marketItem.medianPrice * steamItem.amount

                totalEstimatedValue += itemValue

                valuedItems.add(
                    ValuedInventoryItem(
                        steamItem = steamItem,
                        suggestedPrice = marketItem.suggestedPrice,
                        minPrice = marketItem.minPrice,
                        medianPrice = marketItem.medianPrice,
                        matched = true
                    )
                )

            } else {

                unmatchedItems++

                valuedItems.add(
                    ValuedInventoryItem(
                        steamItem = steamItem,
                        matched = false
                    )
                )
            }
        }

        return InventoryValuation(
            items = valuedItems,
            matchedItems = matchedItems,
            unmatchedItems = unmatchedItems,
            totalEstimatedValue = totalEstimatedValue
        )
    }
}