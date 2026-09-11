package com.team01.steamanalyst.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team01.steamanalyst.components.*
import com.team01.steamanalyst.data.SteamInventoryItem
import com.team01.steamanalyst.ui.theme.*
import java.util.Locale
import androidx.compose.runtime.remember
import com.team01.steamanalyst.data.SteamAccountData
import com.team01.steamanalyst.data.ValuedInventoryItem

@Composable
@Preview
fun InvenScreen() {
    var query by remember { mutableStateOf("") }

    val mockItem1 = SteamInventoryItem(
        "AssetID",
        "ClassID",
        "InstanceID",
        7,
        "DatItem",
        "TrulyItem",
        "MarketHash",
        true,
        false
    )
    val mockItem2 = SteamInventoryItem(
        "AssetID",
        "ClassID",
        "InstanceID",
        3,
        "Game",
        "NotGame",
        "MarketHash",
        false,
        false
    )
    val mockItem3 = SteamInventoryItem(
        "AssetID",
        "ClassID",
        "InstanceID",
        32,
        "ItemOF",
        "Doom",
        "MarketHash",
        false,
        true
    )

    Column(Modifier.fillMaxSize()) {
        TopSearchBar(query = query, onQueryChange = { query = it })

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            /* for (item in SteamAccountData().inventory){
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        RShowItem(Modifier.weight(1.4f), item)
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }*/
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    RShowItem(Modifier.weight(1.4f), mockItem1)
                }
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    RShowItem(Modifier.weight(1.4f), mockItem2)
                }
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    RShowItem(Modifier.weight(1.4f), mockItem3)
                }
            }
        }
    }
}

@Composable
fun RShowItem(modifier: Modifier = Modifier, item : SteamInventoryItem){
    var showDetails :Boolean by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(BgPanel)
            .padding(16.dp)
            .clickable(onClick = {showDetails = !showDetails})
    ) {

        if (!showDetails) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((130.dp))
                    .padding(vertical = 3.dp)
                    .clip(shape = RoundedCornerShape(6.dp))
                    .background(BgPanel)
                    .padding(vertical = 10.dp, horizontal = 8.dp)
            ) {
                //Code for the picture goes here
                Text(
                    text = "PictureHere",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
        Spacer(Modifier.height(5.dp))
        Text(item.name, style = MaterialTheme.typography.labelSmall, color = Purple40)
        Spacer(Modifier.height(5.dp))
        val amount = item.amount
        Text("Amount $amount", style = MaterialTheme.typography.labelSmall, color = Purple40)

        if (showDetails){
            //Only shows when the picture is gone
            Spacer(Modifier.height(10.dp))
            Text("Asset ID: " + item.assetID, style = MaterialTheme.typography.labelSmall, color = Pink40)
            Spacer(Modifier.height(10.dp))
            Text("ClassID: " + item.classID, style = MaterialTheme.typography.labelSmall, color = Pink40)
            Spacer(Modifier.height(10.dp))
            Text("MarketName: " + item.marketName, style = MaterialTheme.typography.labelSmall, color = Purple40)
            Spacer(Modifier.height(10.dp))
            val tradable = item.tradable
            Text("Tradable: $tradable", style = MaterialTheme.typography.labelSmall, color = Purple40)
            Spacer(Modifier.height(10.dp))
            val marketable = item.marketable
            Text("Marketable: $marketable", style = MaterialTheme.typography.labelSmall, color = Purple40)
            Spacer(Modifier.height(10.dp))

        }
    }
}