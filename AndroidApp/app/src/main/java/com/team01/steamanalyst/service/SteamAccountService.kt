package com.team01.steamanalyst.service

import com.team01.steamanalyst.data.SteamAccountData
import com.team01.steamanalyst.valuation.MarketValuation

class SteamAccountService {

    fun loadAccount(
        vanityName: String,
        apiKey: String
    ): SteamAccountData {

        val steamService = SteamService()
        val skinportService = SkinportService()

        val steamID64 =
            steamService.resolveVanityURL(
                vanityName = vanityName,
                apiKey = apiKey
            )

        val profile =
            steamService.fetchPlayerSummary(
                steamID64 = steamID64,
                apiKey = apiKey
            )

        val inventory =
            steamService.fetchInventory(
                steamID64 = steamID64
            )

        val marketItems =
            skinportService.fetchMarketData()

        val valuation =
            MarketValuation().calculate(
                inventory = inventory,
                marketItems = marketItems
            )

        return SteamAccountData(
            profile = profile,
            inventory = inventory,
            valuation = valuation,
            loaded = true
        )
    }
}