package com.team01.steamanalyst.service

import org.junit.Assert.assertTrue
import org.junit.Test

class SteamServiceTest {

    @Test
    fun liveTestInventoryImageUrls() {

        val steamService = SteamService()

        // Replace this with the SteamID64 of a public Steam account
        val steamID64 = System.getenv("STEAM_TEST_ID")
            ?: throw IllegalStateException(
                "STEAM_TEST_ID environment variable is required for this live test"
            )
        val inventory = steamService.fetchInventory(steamID64)

        println("Inventory items returned: ${inventory.size}")

        val itemsWithImages = inventory.filter {
            it.iconUrl.isNotBlank()
        }

        println("Items with image URLs: ${itemsWithImages.size}")

        itemsWithImages.take(10).forEach { item ->
            println("${item.marketHashName}")
            println("Image: ${item.iconUrl}")
            println()
        }

        assertTrue(
            "Steam inventory should contain at least one item with an image URL",
            itemsWithImages.isNotEmpty()
        )

        assertTrue(
            "Image URLs should use HTTPS",
            itemsWithImages.all {
                it.iconUrl.startsWith("https://")
            }
        )
    }
}