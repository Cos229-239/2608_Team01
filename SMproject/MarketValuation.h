#pragma once
#include "SteamAPI.h"
#include "SkinPortAPI.h"
#include <string>
#include <vector>

struct ValuedInventoryItem
{
    SteamInventoryItem steamItem;

    double suggestedPrice = 0.0;
    double minPrice = 0.0;
    double medianPrice = 0.0;

    bool matched = false;
};

struct InventoryValuation
{
    std::vector<ValuedInventoryItem> items;

    int matchedItems = 0;
    int unmatchedItems = 0;

    double totalEstimatedValue = 0.0;
};

class MarketValuation
{
public:
    InventoryValuation CalculateInventoryValue(
        const std::vector<SteamInventoryItem>& inventory,
        const std::vector<SkinPortItem>& marketData);
};

