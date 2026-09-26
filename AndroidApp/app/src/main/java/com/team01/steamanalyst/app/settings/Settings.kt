package com.team01.steamanalyst.app.settings

import androidx.compose.material3.ColorScheme
import com.team01.steamanalyst.ui.theme.DarkColorScheme
import com.team01.steamanalyst.ui.theme.LightColorScheme

class Settings {
    //Inventory
    var showDetailsInventory :Boolean = false
    var showAssetID = true
    var showClassID = true
    var showTradable = true
    var showMarketable = true

    var SortByInventory :String = "NoSort"
    var AscendingInventory : Boolean = false

    //Watchlists
    var priceToShowWatchlists :Int = 1
    var SortByWatchlists :String = "NoSort"
    var AscendingWatchlists : Boolean = false


    var colorScheme = DarkColorScheme
    var color :String = "Dark"
    fun cS() : ColorScheme {
        if (color == "Dark"){
            color = "Light"
            colorScheme = LightColorScheme
            return colorScheme
        }
        else {
            color = "Dark"
            colorScheme = DarkColorScheme
            return colorScheme
        }

    }
}