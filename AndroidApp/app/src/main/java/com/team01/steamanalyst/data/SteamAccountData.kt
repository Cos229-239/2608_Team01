package com.team01.steamanalyst.data

data class SteamAccountData(
    val profile: SteamProfile = SteamProfile(),
    val inventory: List<SteamInventoryItem> = emptyList(),
    val valuation: InventoryValuation = InventoryValuation(),
    val loaded: Boolean = false
)