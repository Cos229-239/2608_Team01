package com.team01.steamanalyst.app.settings

import androidx.compose.material3.ColorScheme
import com.team01.steamanalyst.ui.theme.DarkColorScheme
import com.team01.steamanalyst.ui.theme.LightColorScheme

class Settings {
    var showDetailsInventory :Boolean = false
    var SortByInventory :String = "NoSort"
    var AscendingInventory : Boolean = false

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