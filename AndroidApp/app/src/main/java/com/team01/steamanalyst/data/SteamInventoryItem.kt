package com.team01.steamanalyst.data

data class SteamInventoryItem(
    val assetID: String = "",
    val classID: String = "",
    val instanceID: String = "",
    val amount: Int = 0,
    val name: String = "",
    val marketName: String = "",
    val marketHashName: String = "",
    val tradable: Boolean = false,
    val marketable: Boolean = false
)