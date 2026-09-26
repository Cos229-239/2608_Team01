package com.team01.steamanalyst.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.remember
import coil.compose.AsyncImage
import com.team01.steamanalyst.steamAccount
import com.team01.steamanalyst.settings

@Composable
@Preview
fun InvenScreen() {
    var query by remember { mutableStateOf("") }
    var spaced :Boolean by remember { mutableStateOf(settings.showDetailsInventory) }
    var sortOrder :String by remember { mutableStateOf(settings.SortByInventory) }
    var ascending : Boolean by remember { mutableStateOf(settings.AscendingInventory) }
    Column(Modifier
        .fillMaxSize()
        .background(settings.colorScheme.background)) {
        TopSearchBar(query = query, onQueryChange = { query = it })
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Spacer(Modifier.width(3.dp))
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height((40.dp))
                        .padding(vertical = 3.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .background(settings.colorScheme.primary)
                        .padding(vertical = 10.dp, horizontal = 8.dp)
                        .clickable(onClick = {
                            ascending = !ascending
                            settings.AscendingInventory = ascending
                        })
                ) {
                    if (ascending) Text("Ascending", style = MaterialTheme.typography.labelSmall, color = settings.colorScheme.onSurface)
                    else Text("Descending", style = MaterialTheme.typography.labelSmall, color = settings.colorScheme.onSurface)
                }
                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .height((40.dp))
                        .padding(vertical = 3.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .background(settings.colorScheme.primary)
                        .padding(vertical = 10.dp, horizontal = 8.dp)
                        .clickable(onClick = {
                            sortOrder = switchSort(sortOrder)
                            settings.SortByInventory = sortOrder
                        })
                ) {
                    Text("Sort Type: $sortOrder", style = MaterialTheme.typography.labelSmall, color = settings.colorScheme.onSurface)
                }
            }
            Spacer(Modifier.height(10.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {

            var listItems :List<SteamInventoryItem>
            if (ascending) {
                listItems = when (sortOrder) {
                    "Name" -> steamAccount.inventory.sortedBy { it.name }
                    "MarketName" -> steamAccount.inventory.sortedBy { it.marketName }
                    "Amount" -> steamAccount.inventory.sortedBy { it.amount }
                    "Marketable" -> steamAccount.inventory.sortedBy { it.marketable }
                    "Tradable" -> steamAccount.inventory.sortedBy { it.tradable }
                    else -> steamAccount.inventory
                }
            }
            else {
                listItems = when (sortOrder) {
                    "Name" -> steamAccount.inventory.sortedByDescending { it.name }
                    "MarketName" -> steamAccount.inventory.sortedByDescending { it.marketName }
                    "Amount" -> steamAccount.inventory.sortedByDescending { it.amount }
                    "Marketable" -> steamAccount.inventory.sortedByDescending { it.marketable }
                    "Tradable" -> steamAccount.inventory.sortedByDescending { it.tradable }
                    else -> steamAccount.inventory
                }
            }
             for (item in listItems){
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        spaced = rShowItem(Modifier.weight(1.4f), item, query)
                    }
                    if (spaced) Spacer(Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
fun rShowItem(modifier: Modifier = Modifier, item : SteamInventoryItem, query :String) : Boolean{
    var showDetails :Boolean by remember { mutableStateOf(settings.showDetailsInventory) }
    if (query != "") {
        if (!item.name.contains(query, true) and !item.marketName.contains(query, true)) return false
    }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(settings.colorScheme.surface)
            .padding(16.dp)
            .clickable(onClick = { showDetails = !showDetails })
    ) {

        if (!showDetails) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((130.dp))
                    .padding(vertical = 3.dp)
                    .clip(shape = RoundedCornerShape(6.dp))
                    .background(settings.colorScheme.surface)
                    .padding(vertical = 10.dp, horizontal = 8.dp)
            ) {
                AsyncImage(
                    model = item.iconUrl,
                    contentDescription = "none",
                    modifier = Modifier.clip(CircleShape)
                )
            }
        }
        Spacer(Modifier.height(5.dp))
        Text(item.name, style = MaterialTheme.typography.labelSmall, color = settings.colorScheme.primary)
        Spacer(Modifier.height(5.dp))
        val amount = item.amount
        Text("Amount $amount", style = MaterialTheme.typography.labelSmall, color = settings.colorScheme.primary)

        if (showDetails){
            //Only shows when the picture is gone
            if (settings.showAssetID) {
                Spacer(Modifier.height(10.dp))
                Text(
                    "Asset ID: " + item.assetID,
                    style = MaterialTheme.typography.labelSmall,
                    color = settings.colorScheme.onSurface
                )
            }
            if (settings.showClassID) {
                Spacer(Modifier.height(10.dp))
                Text(
                    "ClassID: " + item.classID,
                    style = MaterialTheme.typography.labelSmall,
                    color = settings.colorScheme.onSurface
                )
            }
            Spacer(Modifier.height(10.dp))
            Text("MarketName: " + item.marketName, style = MaterialTheme.typography.labelSmall, color = settings.colorScheme.primary)
            if (settings.showTradable) {
                Spacer(Modifier.height(10.dp))
                Text(
                    "Tradable: ${item.tradable}",
                    style = MaterialTheme.typography.labelSmall,
                    color = settings.colorScheme.onSurface
                )
            }
            if (settings.showMarketable) {
                Spacer(Modifier.height(10.dp))
                Text(
                    "Marketable: ${item.marketable}",
                    style = MaterialTheme.typography.labelSmall,
                    color = settings.colorScheme.onSurface
                )
            }
            Spacer(Modifier.height(4.dp))
        }
    }
    return true
}
private fun switchSort(sortOrder:String):String{
    if (sortOrder == "NoSort") return "Name"
    if (sortOrder == "Name") return "MarketName"
    if (sortOrder == "MarketName") return "Amount"
    if (sortOrder == "Amount") return "Marketable"
    if (sortOrder == "Marketable") return "Tradable"
    return "NoSort"
}