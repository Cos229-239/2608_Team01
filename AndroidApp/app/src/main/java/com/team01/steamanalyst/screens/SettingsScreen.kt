package com.team01.steamanalyst.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team01.steamanalyst.settings

@Composable
@Preview
fun SettingsScreen(){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color(0x44666688))
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Spacer(Modifier.height(8.dp))
                    ShowSettings(modifier = Modifier.weight(1.4f))
            }
        }
    }
}

@Composable
fun ShowSettings(modifier: Modifier = Modifier){
    var colortype : String by remember { mutableStateOf(settings.color) }
    var color by remember { mutableStateOf(settings.colorScheme) }

    Spacer(Modifier.height(8.dp))
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.surface)
            .padding(16.dp)
    )
    {
        Text(
            "Settings",
            style = MaterialTheme.typography.bodyLarge,
            color = color.onSurface
        )
        Text(
            "---------------------------------------------------------------------",
            style = MaterialTheme.typography.bodySmall,
            color = color.onSurface
        )
        Spacer(Modifier.height(8.dp))
        Box( modifier = Modifier
            .background(color.surface)
            .width(75.dp)
            .height(25.dp)
            .clip(shape = RoundedCornerShape(6.dp))
            .clickable(onClick = {
                settings.cS()
                colortype = settings.color
                color = settings.colorScheme
            })
            .background(color.primary)
            .padding(vertical = 5.dp, horizontal = 7.dp))
        {
            Text(colortype, style = MaterialTheme.typography.bodySmall, color = color.onBackground)
        }
    }
    Spacer(Modifier.height(8.dp))
    //Inventory
    var showDetails :Boolean by remember { mutableStateOf(settings.showDetailsInventory) }
    var showAssetID by remember { mutableStateOf(settings.showAssetID) }
    var showClassID by remember { mutableStateOf(settings.showClassID) }
    var showTradable by remember { mutableStateOf(settings.showTradable) }
    var showMarketable by remember { mutableStateOf(settings.showMarketable) }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.surface)
            .padding(16.dp)
    )
    {
        Text(
            "Inventory",
            style = MaterialTheme.typography.bodyMedium,
            color = color.onSurface
        )
        Text(
            "change inventory UI elements here...",
            style = MaterialTheme.typography.bodySmall,
            color = color.onSurface
        )
        Text(
            "---------------------------------------------------------------------",
            style = MaterialTheme.typography.bodySmall,
            color = color.onSurface
        )
        Box( modifier = Modifier
            .background(settings.colorScheme.surface)
            .width(225.dp)
            .height(25.dp)
            .clip(shape = RoundedCornerShape(6.dp))
            .clickable(onClick = {
                showDetails = !showDetails
                settings.showDetailsInventory = showDetails
            })
            .background(color.primary)
            .padding(vertical = 5.dp, horizontal = 7.dp))
        {
            val showing = when(showDetails){
                true -> "details"
                else -> "picture"
            }
            Text("Show $showing in inventory by default", style = MaterialTheme.typography.bodySmall, color = color.onBackground)
        }
        Spacer(Modifier.height(3.dp))
        Box( modifier = Modifier
            .background(settings.colorScheme.surface)
            .width(225.dp)
            .height(25.dp)
            .clip(shape = RoundedCornerShape(6.dp))
            .clickable(onClick = {
                showAssetID = !showAssetID
                settings.showAssetID = showAssetID
            })
            .background(color.primary)
            .padding(vertical = 5.dp, horizontal = 7.dp))
        {
            Text("Show AssetID: $showAssetID", style = MaterialTheme.typography.bodySmall, color = color.onBackground)
        }
        Spacer(Modifier.height(3.dp))
        Box( modifier = Modifier
            .background(settings.colorScheme.surface)
            .width(225.dp)
            .height(25.dp)
            .clip(shape = RoundedCornerShape(6.dp))
            .clickable(onClick = {
                showClassID = !showClassID
                settings.showClassID = showClassID
            })
            .background(color.primary)
            .padding(vertical = 5.dp, horizontal = 7.dp))
        {
            Text("Show ClassID: $showClassID", style = MaterialTheme.typography.bodySmall, color = color.onBackground)
        }
        Spacer(Modifier.height(3.dp))
        Box( modifier = Modifier
            .background(settings.colorScheme.surface)
            .width(225.dp)
            .height(25.dp)
            .clip(shape = RoundedCornerShape(6.dp))
            .clickable(onClick = {
                showTradable = !showTradable
                settings.showTradable = showTradable
            })
            .background(color.primary)
            .padding(vertical = 5.dp, horizontal = 7.dp))
        {
            Text("Show Tradable: $showTradable", style = MaterialTheme.typography.bodySmall, color = color.onBackground)
        }
        Spacer(Modifier.height(3.dp))
        Box( modifier = Modifier
            .background(settings.colorScheme.surface)
            .width(225.dp)
            .height(25.dp)
            .clip(shape = RoundedCornerShape(6.dp))
            .clickable(onClick = {
                showMarketable = !showMarketable
                settings.showMarketable = showMarketable
            })
            .background(color.primary)
            .padding(vertical = 5.dp, horizontal = 7.dp))
        {
            Text("Show Marketable: $showMarketable", style = MaterialTheme.typography.bodySmall, color = color.onBackground)
        }
    }
    Spacer(Modifier.height(8.dp))
    //Watchlists
    var priceToShow by remember { mutableIntStateOf(settings.priceToShowWatchlists) }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.surface)
            .padding(16.dp)
    )
    {
        Text(
            "Watchlists",
            style = MaterialTheme.typography.bodyMedium,
            color = color.onSurface
        )
        Text(
            "change watchlist UI elements here...",
            style = MaterialTheme.typography.bodySmall,
            color = color.onSurface
        )
        Text(
            "---------------------------------------------------------------------",
            style = MaterialTheme.typography.bodySmall,
            color = color.onSurface
        )
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Box( modifier = Modifier
                .background(settings.colorScheme.surface)
                .width(130.dp)
                .height(25.dp)
                .clip(shape = RoundedCornerShape(6.dp))
                .clickable(onClick = {
                    priceToShow = Rswitch(priceToShow)
                    settings.priceToShowWatchlists = priceToShow
                })
                .background(color.primary)
                .padding(vertical = 5.dp, horizontal = 7.dp))
            {
                Text("Price in Watchlist", style = MaterialTheme.typography.bodySmall, color = color.onBackground)
            }
            Box( modifier = Modifier
                .background(settings.colorScheme.surface)
                .width(80.dp)
                .height(25.dp)
                .clip(shape = RoundedCornerShape(6.dp))
                .background(settings.colorScheme.surface)
                .padding(vertical = 5.dp, horizontal = 7.dp))
            {
                val showing = when(priceToShow){
                    2 -> "Median"
                    3 -> "Minimum"
                    else -> "Suggested"
                }
                Text(showing, style = MaterialTheme.typography.bodySmall, color = color.primary)
            }
        }
    }
    }
private fun Rswitch(swap :Int) :Int {
    if (swap == 1) return 2
    if (swap == 2) return 3
    return 1
}