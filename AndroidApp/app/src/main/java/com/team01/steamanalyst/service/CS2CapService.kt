package com.team01.steamanalyst.service

import com.team01.steamanalyst.BuildConfig
import com.team01.steamanalyst.data.MarketplacePrice
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

class CS2CapService {

    private val client = OkHttpClient()

    fun fetchPrices(marketHashName: String): List<MarketplacePrice> {

        val url = "https://api.cs2c.app/v1/prices"
            .toHttpUrl()
            .newBuilder()
            .addQueryParameter("market_hash_name", marketHashName)
            .build()

        val request = Request.Builder()
            .url(url)
            .addHeader(
                "Authorization",
                "Bearer ${BuildConfig.CS2CAP_API_KEY}"
            )
            .build()

        client.newCall(request).execute().use { response ->

            if (!response.isSuccessful) {
                throw IllegalStateException(
                    "CS2Cap request failed with HTTP ${response.code}"
                )
            }

            val body = response.body?.string()
                ?: throw IllegalStateException("CS2Cap returned an empty response")
            return parsePrices(body)
        }
    }

    private fun parsePrices(json: String): List<MarketplacePrice> {

        val root = JSONObject(json)

        val prices = root.getJSONArray("items")

        val results = mutableListOf<MarketplacePrice>()

        for (i in 0 until prices.length()) {

            val item = prices.getJSONObject(i)

            val lowestAsk = if (item.isNull("lowest_ask")) {
                null
            } else {
                item.getDouble("lowest_ask") / 100.0
            }

            results.add(
                MarketplacePrice(
                    provider = item.optString("provider"),
                    lowestAsk = lowestAsk,
                    quantity = if (item.isNull("quantity")) {
                        null
                    } else {
                        item.optInt("quantity")
                    },
                    listingUrl = item.optString("url")
                        .takeIf { it.isNotBlank() },
                    lastUpdated = item.optString("last_updated")
                        .takeIf { it.isNotBlank() }
                )
            )
        }

        return results
    }
}