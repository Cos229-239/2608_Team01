package com.team01.steamanalyst.valuation

import com.team01.steamanalyst.data.SkinPortItem
import com.team01.steamanalyst.data.TopMover
import kotlin.math.abs

class TopMoversAnalyzer {
    fun findTopMovers(
        previousCatalog: List<SkinPortItem>,
        currentCatalog: List<SkinPortItem>,
        limit: Int = 3
    ): List<TopMover>{
        if(previousCatalog.isEmpty()) return emptyList()
        val previousMap = previousCatalog.associateBy { it.marketHashName }

        return currentCatalog
            .mapNotNull { current ->
                val previous = previousMap[current.marketHashName] ?: return@mapNotNull null
                if(previous.medianPrice <= 0.0) return@mapNotNull  null
                val change = ((current.medianPrice - previous.medianPrice) / previous.medianPrice) * 100.0
                TopMover(current.marketHashName, current.medianPrice, previous.medianPrice, change)
            }
            .filter { it.percentChange != 0.0 }
            .sortedByDescending { abs(it.percentChange) }
            .take(limit)
    }
}