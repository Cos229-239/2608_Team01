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
import com.team01.steamanalyst.service.SkinportService
import com.team01.steamanalyst.ui.theme.SteamAnalystTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SteamAnalystTheme {

                var status by remember {
                    mutableStateOf("Loading Skinport data...")
                }

                LaunchedEffect(Unit) {
                    try {
                        val items = withContext(Dispatchers.IO) {
                            SkinportService().fetchMarketData()
                        }

                        status = "Skinport items loaded: ${items.size}"

                        Log.d(
                            "SteamAnalyst",
                            "Skinport returned ${items.size} items"
                        )

                        if (items.isNotEmpty()) {
                            Log.d(
                                "SteamAnalyst",
                                "First item: ${items[0]}"
                            )
                        }

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