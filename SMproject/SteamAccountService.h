#pragma once
#include "SteamAPI.h"
#include "SkinPortAPI.h"
#include "MarketValuation.h"
#include <string>

struct SteamAccountData
{
    SteamProfile profile;
    std::vector<SteamInventoryItem> inventory;
    InventoryValuation valuation;
    bool loaded = false;
};

class SteamAccountService
{
public:
    SteamAccountData LoadAccount(const std::string& vanityName);
};
