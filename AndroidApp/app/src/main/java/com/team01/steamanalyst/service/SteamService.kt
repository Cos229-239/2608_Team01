package com.team01.steamanalyst.service
import com.team01.steamanalyst.data.SteamInventoryItem
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class SteamService {

    private val client = OkHttpClient()
    fun resolveVanityURL(vanityName: String, apiKey: String): String {

        val request = Request.Builder()
            .url(
                "https://api.steampowered.com/ISteamUser/ResolveVanityURL/v1/" +
                        "?key=$apiKey&vanityurl=$vanityName"
            )
            .build()

        client.newCall(request).execute().use { response ->

            if (!response.isSuccessful) {
                throw Exception("Steam vanity request failed: HTTP ${response.code}")
            }

            val responseBody = response.body?.string()
                ?: throw Exception("Steam returned an empty vanity response")

            val json = JSONObject(responseBody)
            val responseObject = json.getJSONObject("response")

            if (responseObject.optInt("success", 0) != 1) {
                throw Exception("Steam vanity URL could not be resolved")
            }

            return responseObject.optString("steamid", "")
        }
    }
    fun fetchPlayerSummary(
        steamID64: String,
        apiKey: String
    ): com.team01.steamanalyst.data.SteamProfile {

        val request = Request.Builder()
            .url(
                "https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/" +
                        "?key=$apiKey&steamids=$steamID64"
            )
            .build()

        client.newCall(request).execute().use { response ->

            if (!response.isSuccessful) {
                throw Exception(
                    "Steam profile request failed: HTTP ${response.code}"
                )
            }

            val responseBody = response.body?.string()
                ?: throw Exception("Steam returned an empty profile response")

            val json = JSONObject(responseBody)

            val players = json
                .getJSONObject("response")
                .getJSONArray("players")

            if (players.length() == 0) {
                throw Exception("Steam profile was not found")
            }

            val player = players.getJSONObject(0)

            return com.team01.steamanalyst.data.SteamProfile(
                steamID = player.optString("steamid", ""),
                personaName = player.optString("personaname", ""),
                profileURL = player.optString("profileurl", ""),
                avatarURL = player.optString("avatarfull", ""),
                communityVisibilityState =
                    player.optInt("communityvisibilitystate", 0),
                personaState =
                    player.optInt("personastate", 0)
            )
        }
    }
    fun fetchInventory(steamID64: String): List<SteamInventoryItem> {

        val request = Request.Builder()
            .url("https://steamcommunity.com/inventory/$steamID64/730/2")
            .build()

        client.newCall(request).execute().use { response ->

            if (!response.isSuccessful) {
                throw Exception("Steam inventory request failed: HTTP ${response.code}")
            }
            val responseBody = response.body?.string()
                ?: throw Exception("Steam returned an empty inventory response")
            val json = JSONObject(responseBody)
            val assets = json.optJSONArray("assets")
                ?: return emptyList()
            val descriptions = json.optJSONArray("descriptions")
                ?: return emptyList()
            val items = mutableListOf<SteamInventoryItem>()

            for (i in 0 until assets.length()) {
                val asset = assets.getJSONObject(i)

                val classID = asset.optString("classid", "")
                val instanceID = asset.optString("instanceid", "")

                var matchingDescription: JSONObject? = null

                for (j in 0 until descriptions.length()) {
                    val description = descriptions.getJSONObject(j)

                    if (
                        description.optString("classid", "") == classID &&
                        description.optString("instanceid", "") == instanceID
                    ) {
                        matchingDescription = description
                        break
                    }
                }

                if (matchingDescription != null) {
                    items.add(
                        SteamInventoryItem(
                            assetID = asset.optString("assetid", ""),
                            classID = classID,
                            instanceID = instanceID,
                            amount = asset.optString("amount", "0").toIntOrNull() ?: 0,
                            name = matchingDescription.optString("name", ""),
                            marketName = matchingDescription.optString("market_name", ""),
                            marketHashName = matchingDescription.optString("market_hash_name", ""),
                            tradable = matchingDescription.optInt("tradable", 0) == 1,
                            marketable = matchingDescription.optInt("marketable", 0) == 1
                        )
                    )
                }
            }

            return items
        }
    }
}