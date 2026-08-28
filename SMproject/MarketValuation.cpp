#include "MarketValuation.h"

InventoryValuation MarketValuation::CalculateInventoryValue(
    const std::vector<SteamInventoryItem>& inventory,
    const std::vector<SkinPortItem>& marketData)
{
    InventoryValuation valuation;

    for (const auto& steamItem : inventory)
    {
        ValuedInventoryItem valuedItem;
        valuedItem.steamItem = steamItem;

        for (const auto& marketItem : marketData)
        {
            if (steamItem.marketHashName == marketItem.marketHashName)
            {
                valuedItem.suggestedPrice = marketItem.suggestedPrice;
                valuedItem.minPrice = marketItem.minPrice;
                valuedItem.medianPrice = marketItem.medianPrice;
                valuedItem.matched = true;

                valuation.matchedItems++;

                valuation.totalEstimatedValue +=
                    marketItem.medianPrice * steamItem.amount;

                break;
            }
        }
        if (!valuedItem.matched)
        {
            valuation.unmatchedItems++;
        }
        valuation.items.push_back(valuedItem);
    }
    return valuation;
}