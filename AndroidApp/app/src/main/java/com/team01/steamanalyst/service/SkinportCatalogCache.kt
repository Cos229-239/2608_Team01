package com.team01.steamanalyst.service

import com.team01.steamanalyst.data.SkinPortItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SkinportCatalogCache {
    private var cached: List<SkinPortItem>? = null
    private var cachedAt: Long = 0
    private val CACHE_LIFETIME_MS =15 * 60 * 1000L

    suspend fun get(skinportService: SkinportService) : List<SkinPortItem>{
        val now = System.currentTimeMillis()
        val current = cached
        if(current != null && now - cachedAt <CACHE_LIFETIME_MS){
            return current
        }
        val fresh = withContext(Dispatchers.IO) {skinportService.fetchMarketData()}
        cached = fresh
        cachedAt = now
        return fresh

    }
}