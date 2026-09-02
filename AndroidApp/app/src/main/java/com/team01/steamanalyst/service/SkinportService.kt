package com.team01.steamanalyst.service

import com.team01.steamanalyst.data.SkinPortItem
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.brotli.BrotliInterceptor
import org.json.JSONArray

class SkinportService {

    private val client = OkHttpClient.Builder()
        .addInterceptor(BrotliInterceptor)
        .build()

    fun fetchMarketData(): List<SkinPortItem> {

        val request = Request.Builder()
            .url("https://api.skinport.com/v1/items?app_id=730&currency=USD&tradable=1")
            .build()

        client.newCall(request).execute().use { response ->

            if (!response.isSuccessful) {
                throw Exception("Skinport request failed: HTTP ${response.code}")
            }

            val responseBody = response.body?.string()
                ?: throw Exception("Skinport returned an empty response")

            val jsonArray = JSONArray(responseBody)
            val items = mutableListOf<SkinPortItem>()

            for (i in 0 until jsonArray.length()) {

                val jsonItem = jsonArray.getJSONObject(i)

                items.add(
                    SkinPortItem(
                        marketHashName = jsonItem.optString(
                            "market_hash_name",
                            ""
                        ),
                        suggestedPrice = jsonItem.optDouble(
                            "suggested_price",
                            0.0
                        ),
                        minPrice = jsonItem.optDouble(
                            "min_price",
                            0.0
                        ),
                        medianPrice = jsonItem.optDouble(
                            "median_price",
                            0.0
                        ),
                        quantity = jsonItem.optInt(
                            "quantity",
                            0
                        )
                    )
                )
            }

            return items
        }
    }
}