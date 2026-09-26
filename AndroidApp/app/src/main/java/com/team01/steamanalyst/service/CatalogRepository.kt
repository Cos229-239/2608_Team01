package com.team01.steamanalyst.service

import com.team01.steamanalyst.data.SkinPortItem
import com.team01.steamanalyst.data.TopMover
import com.team01.steamanalyst.valuation.TopMoversAnalyzer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CatalogRepository {

    private val skinportService = SkinportService()
    private val topMoversAnalyzer = TopMoversAnalyzer()

    var currentCatalog: List<SkinPortItem> = emptyList()
        private set

    private var previousCatalog: List<SkinPortItem> = emptyList()

    var topMovers: List<TopMover> = emptyList()
        private set

    private var lastRefreshedAt:Long = 0
    private val MIN_REFRESH_iNTERVAL_MS = 5 * 60* 1000L

    suspend fun refresh(force: Boolean = false){

        val now = System.currentTimeMillis()
        if(!force && currentCatalog.isNotEmpty() && now - lastRefreshedAt < MIN_REFRESH_iNTERVAL_MS){
           return
        }

        val newCatalog = withContext(Dispatchers.IO) { skinportService.fetchMarketData()}
        if (currentCatalog.isNotEmpty()){
            previousCatalog = currentCatalog
        }
        currentCatalog = newCatalog
        topMovers = topMoversAnalyzer.findTopMovers(previousCatalog, currentCatalog)
    }
    fun randomSample(count: Int) : List<SkinPortItem> =
        if(currentCatalog.isNotEmpty()) emptyList() else currentCatalog.shuffled().take(count)
}