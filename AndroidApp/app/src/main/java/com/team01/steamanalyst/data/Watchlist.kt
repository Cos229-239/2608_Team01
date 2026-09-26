package com.team01.steamanalyst.data

data class Watchlist(
    val id: String,
    val name: String,
    val itemHashNames: MutableList<String> = mutableListOf()
)

data class WatchListCollection(
    val lists: MutableList<Watchlist> = mutableListOf(
        Watchlist(id = "default", name = "My Watchlist")
    ),
    var activeListId: String = "default"
)
