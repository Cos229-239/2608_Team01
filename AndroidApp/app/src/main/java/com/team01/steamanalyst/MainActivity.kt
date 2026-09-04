package com.team01.steamanalyst

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.team01.steamanalyst.ui.theme.SteamAnalystTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SteamAnalystTheme {
                Text(text = "Steam Analyst")
            }
        }
    }
}