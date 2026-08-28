#include "SteamAccountService.h"

SteamAccountData SteamAccountService::LoadAccount(
    const std::string& vanityName)
{
    SteamAccountData accountData;
    SteamAPI steam;
    SkinPortAPI skinport;
    MarketValuation valuationCalculator;

    std::string steamID =
        steam.ResolveVanityURL(vanityName);

    if (steamID.empty())
    {
        return accountData;
    }

    accountData.profile =
        steam.FetchPlayerSummary(steamID);
    accountData.inventory =
        steam.FetchCS2Inventory(steamID);
    std::vector<SkinPortItem> marketData =
        skinport.FetchMarketData();
    accountData.valuation =
        valuationCalculator.CalculateInventoryValue(
            accountData.inventory,
            marketData);
    accountData.loaded = true;

    return accountData;
}