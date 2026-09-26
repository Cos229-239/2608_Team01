package com.team01.steamanalyst.valuation

import com.team01.steamanalyst.data.Watchlist
import com.team01.steamanalyst.watchlists
import java.util.UUID

object WatchlistManager {

    fun createWatchlist(name: String) : Watchlist{
    val newList = Watchlist(id = UUID.randomUUID().toString(), name = name)
        watchlists.lists.add(newList)
        return newList
    }

    fun deleteWatchlist(id: String){
        if(watchlists.lists.size <= 1) return
        watchlists.lists.removeAll {it.id == id}
        if(watchlists.activeListId == id) {
            watchlists.activeListId = watchlists.lists.first().id
        }
    }

    fun setActiveWatchlist(id: String) {
        if(watchlists.lists.any { it.id == id}) watchlists.activeListId = id
    }

    fun addItem(watchlistId: String, marketHashName: String){
        watchlists.lists.find { it.id == watchlistId} ?.let{
            if(!it.itemHashNames.contains(marketHashName)) it.itemHashNames.add(marketHashName)
        }
    }

    fun removeItem(watchlistId: String, marketHashName: String){
        watchlists.lists.find{it.id == watchlistId }?.itemHashNames?.remove(marketHashName)
    }

    fun getActiveWatchlist(): Watchlist =
        watchlists.lists.find { it.id == watchlists.activeListId } ?: watchlists.lists.first()
}