#pragma once
#include <string>
#include <vector>

static size_t WriteCallback(void* contents, size_t size, size_t nmemb, void* userp)
{
    size_t totalSize = size * nmemb;

    std::string* response = static_cast<std::string*>(userp);

    response->append(static_cast<char*>(contents), totalSize);

    return totalSize;
}

struct SkinPortItem
{
    std::string marketHashName;
    double suggestedPrice = 0.0;
    double minPrice = 0.0;
    double medianPrice = 0.0;
    int quantity = 0;
};

class SkinPortAPI
{
public:
    std::vector<SkinPortItem> FetchMarketData();
};

