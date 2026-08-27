#include "SkinPortAPI.h"
#include <curl/curl.h>
#include <nlohmann/json.hpp>

std::vector<SkinPortItem> SkinPortAPI::FetchMarketData()
{
    std::vector<SkinPortItem> items;

    CURL* curl = curl_easy_init();

    if (curl == nullptr)
    {
        return items;
    }

    std::string response;

    curl_easy_setopt(
        curl,
        CURLOPT_URL,
        "https://api.skinport.com/v1/items?app_id=730&currency=USD&tradable=1"
    );
    curl_easy_setopt(
        curl,
        CURLOPT_WRITEDATA,
        &response
    );
    curl_easy_setopt(
        curl,
        CURLOPT_WRITEFUNCTION,
        WriteCallback
    );
    curl_easy_setopt(
        curl,
        CURLOPT_ACCEPT_ENCODING,
        "br"
    );

    CURLcode result = curl_easy_perform(curl);

    if (result != CURLE_OK)
    {
        curl_easy_cleanup(curl);
        return items;
    }

    nlohmann::json data = nlohmann::json::parse(response);

    for (const auto& jsonItem : data)
    {
        SkinPortItem item;

        item.marketHashName =
            jsonItem.value("market_hash_name", "");

        item.suggestedPrice =
            jsonItem["suggested_price"].is_number()
            ? jsonItem["suggested_price"].get<double>()
            : 0.0;

        item.minPrice =
            jsonItem["min_price"].is_number()
            ? jsonItem["min_price"].get<double>()
            : 0.0;

        item.medianPrice =
            jsonItem["median_price"].is_number()
            ? jsonItem["median_price"].get<double>()
            : 0.0;

        item.quantity =
            jsonItem["quantity"].is_number_integer()
            ? jsonItem["quantity"].get<int>()
            : 0;

        items.push_back(item);
    }
    curl_easy_cleanup(curl);
    return items;
}