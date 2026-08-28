#include "SteamAPI.h"
#include <curl/curl.h>
#include <nlohmann/json.hpp>
#include <string>
#include <cstdlib>

static size_t WriteCallback(void* contents, size_t size, size_t nmemb, void* userp)
{
    size_t totalSize = size * nmemb;

    std::string* response = static_cast<std::string*>(userp);
    response->append(static_cast<char*>(contents), totalSize);

    return totalSize;
}

SteamProfile SteamAPI::FetchPlayerSummary(const std::string& steamID)
{
    SteamProfile profile;
    std::string response;
    char* apiKey = nullptr;
    size_t apiKeyLength = 0;

    _dupenv_s(&apiKey, &apiKeyLength, "STEAM_API_KEY");

    if (apiKey == nullptr)
    {
        return profile;
    }
    CURLcode result = CURLE_FAILED_INIT;
    CURL* curl = curl_easy_init();
    if (curl)
    {
        std::string url =
            "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/?key=" +
            std::string(apiKey) +
            "&steamids=" +
            steamID;
        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &response);
        result = curl_easy_perform(curl);
        curl_easy_cleanup(curl);
    }

    free(apiKey);

    if (result == CURLE_OK && !response.empty())
    {
        nlohmann::json jsonResponse = nlohmann::json::parse(response);

        if (jsonResponse.contains("response") &&
            jsonResponse["response"].contains("players") &&
            !jsonResponse["response"]["players"].empty())
        {
            const auto& player = jsonResponse["response"]["players"][0];

            profile.steamID = player.value("steamid", "");
            profile.personaName = player.value("personaname", "");
            profile.profileURL = player.value("profileurl", "");
            profile.avatarURL = player.value("avatarfull", "");
            profile.communityVisibilityState =
                player.value("communityvisibilitystate", 0);
            profile.personaState =
                player.value("personastate", 0);
        }
    }

    return profile;
}

std::vector<SteamInventoryItem> SteamAPI::FetchCS2Inventory(
    const std::string& steamID)
{
    std::vector<SteamInventoryItem> inventory;
    std::string response;
    CURL* curl = curl_easy_init();

    if (curl)
    {
        std::string url =
            "https://steamcommunity.com/inventory/" +
            steamID +
            "/730/2";
        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &response);

        CURLcode result = curl_easy_perform(curl);

        curl_easy_cleanup(curl);

        if (result != CURLE_OK || response.empty())
        {
            return inventory;
        }

        nlohmann::json jsonResponse =
            nlohmann::json::parse(response, nullptr, false);

        if (jsonResponse.is_discarded())
        {
            return inventory;
        }

        if (!jsonResponse.contains("assets") ||
            !jsonResponse.contains("descriptions"))
        {
            return inventory;
        }

        for (const auto& asset : jsonResponse["assets"])
        {
            SteamInventoryItem item;

            item.assetID = asset.value("assetid", "");
            item.classID = asset.value("classid", "");
            item.instanceID = asset.value("instanceid", "");
            item.amount = std::stoi(asset.value("amount", "0"));

            for (const auto& description : jsonResponse["descriptions"])
            {
                if (description.value("classid", "") == item.classID &&
                    description.value("instanceid", "") == item.instanceID)
                {
                    item.name = description.value("name", "");
                    item.marketName = description.value("market_name", "");
                    item.marketHashName =
                        description.value("market_hash_name", "");

                    item.tradable =
                        description.value("tradable", 0) == 1;

                    item.marketable =
                        description.value("marketable", 0) == 1;

                    break;
                }
            }

            inventory.push_back(item);
        }
    }
    return inventory;
}

