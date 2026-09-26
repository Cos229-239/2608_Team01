package com.team01.steamanalyst

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.team01.steamanalyst.app.settings.Settings
import com.team01.steamanalyst.data.SteamAccountData
import com.team01.steamanalyst.data.WatchListCollection
import com.team01.steamanalyst.ui.theme.SteamAnalystTheme
import com.team01.steamanalyst.navigation.SteamAnalystNav
import com.team01.steamanalyst.service.CatalogRepository




var settings : Settings = Settings()
var steamAccount : SteamAccountData = SteamAccountData()
var vanityName :String = ""
var apiKey :String = ""

var watchlists: WatchListCollection = WatchListCollection()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SteamAnalystTheme {
                SteamAnalystNav()

                var status by remember {
                    mutableStateOf("Loading Skinport data...")
                }

                LaunchedEffect(Unit) {
                    try {
                       CatalogRepository.refresh()
                        status ="Skinport items loaded: ${CatalogRepository.currentCatalog.size}"

                        Log.d(

                            "SteamAnalyst",
                            "Skinport returned ${CatalogRepository.currentCatalog.size} items"
                        )

                    } catch (e: Exception) {
                        status = "Skinport error: ${e.message}"

                        Log.e(
                            "SteamAnalyst",
                            "Skinport request failed",
                            e
                        )
                    }
                }

                Text(text = status)
            }
        }
    }
}