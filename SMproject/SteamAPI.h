#pragma once
#include <string>
#include <vector>

struct SteamProfile
{
    std::string steamID;
    std::string personaName;
    std::string profileURL;
    std::string avatarURL;
    int communityVisibilityState = 0;
    int personaState = 0;
};

struct SteamInventoryItem
{
    std::string assetID;
    std::string classID;
    std::string instanceID;
    int amount = 0;

    std::string name;
    std::string marketName;
    std::string marketHashName;

    bool tradable = false;
    bool marketable = false;
};

class SteamAPI
{
public: 
    SteamProfile FetchPlayerSummary(const std::string& steamID);
    std::string ResolveVanityURL(const std::string& vanityName);

    std::vector<SteamInventoryItem> FetchCS2Inventory(
        const std::string& steamID);
};

