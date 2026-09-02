package com.team01.steamanalyst.data

data class ValuedInventoryItem(
    val steamItem: SteamInventoryItem,
    val suggestedPrice: Double = 0.0,
    val minPrice: Double = 0.0,
    val medianPrice: Double = 0.0,
    val matched: Boolean = false
)

data class InventoryValuation(
    val items: List<ValuedInventoryItem> = emptyList(),
    val matchedItems: Int = 0,
    val unmatchedItems: Int = 0,
    val totalEstimatedValue: Double = 0.0
)