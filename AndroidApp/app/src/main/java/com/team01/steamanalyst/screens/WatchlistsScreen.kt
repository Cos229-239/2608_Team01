package com.team01.steamanalyst.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team01.steamanalyst.components.*
import com.team01.steamanalyst.data.SkinPortItem
import com.team01.steamanalyst.data.Watchlist
import com.team01.steamanalyst.valuation.WatchlistManager
import com.team01.steamanalyst.watchlists
import com.team01.steamanalyst.data.SteamInventoryItem
import com.team01.steamanalyst.service.CatalogRepository
import com.team01.steamanalyst.steamAccount
import com.team01.steamanalyst.settings
import com.team01.steamanalyst.ui.theme.*


@Composable
@Preview
fun WatchScreen(){
    var query by remember {mutableStateOf("")}
    var refreshTick by remember { mutableIntStateOf(0) }
    var addingList by remember { mutableStateOf(false) }
    var newListName by remember { mutableStateOf("") }
    var addingFromInventory by remember { mutableStateOf(false) }

    val activeList = WatchlistManager.getActiveWatchlist()

    Column(Modifier.fillMaxSize()){
        TopSearchBar(query = query, onQueryChange = {query = it})

        WatchlistSelectorRow(
            lists = watchlists.lists,
            activeId = watchlists.activeListId,
            onSelect = { WatchlistManager.setActiveWatchlist(it); refreshTick++ },
            onAddClick = {addingList = true },
            onDeleteClick = { WatchlistManager.deleteWatchlist(it); refreshTick ++}
        )
        if(addingList){
            NewWatchlistRow(
                name = newListName,
                onNameChange = { newListName = it},
                onConfirm = {
                    if(newListName.isNotBlank()) WatchlistManager.createWatchlist(newListName)
                    newListName = ""; addingList = false; refreshTick++
                },
                onCancel = {newListName = ""; addingList = false}

            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.End
        ){
            Text(
                text = if(addingFromInventory) "Close" else "Add from Inventory",
                style = MaterialTheme.typography.labelSmall,
                color = AccentBlue,
                modifier = Modifier.clickable{addingFromInventory = !addingFromInventory}
            )
        }
        key(refreshTick){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp)
            ) {
                if(addingFromInventory){

                    val availableFromInventory = steamAccount.inventory
                        .filter { it.marketHashName.isNotBlank() }
                        .filter { !activeList.itemHashNames.contains(it.marketHashName) }
                        .filter { query.isNotBlank() || it.name.contains(query, true) || it.marketName.contains(query, true) }
                        .distinctBy { it.marketHashName }

                    item{
                        Text(
                            "From your Inventory",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextPrimary
                        )
                    }
                    if(availableFromInventory.isEmpty()){
                        item{
                            Text(
                                if(!steamAccount.loaded)"Log in to see your inventory"
                                else "All your inventory items are already on this watchlist",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextSecondary
                            )
                        }
                    }else{
                        items(availableFromInventory, key = {it.assetID}) {invItem ->
                            InventoryAddRow(invItem){
                                WatchlistManager.addItem(activeList.id, invItem.marketHashName)
                                refreshTick++
                            }
                        }
                    }
                    item {Spacer(Modifier.height(4.dp))}
                }

                val watchedItems = activeList.itemHashNames.mapNotNull { hash ->
                    CatalogRepository.currentCatalog.find{it.marketHashName == hash}
                }

                if(watchedItems.isEmpty()){
                    item{EmptyWatchlistCard()}
                }else{
                    items(watchedItems, key = {it.marketHashName}) {skin ->
                        if(query.isBlank() || skin.marketHashName.contains(query, true)){
                            WatchedItemRow(skin) {
                                WatchlistManager.removeItem(activeList.id, skin.marketHashName)
                                refreshTick++
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WatchlistSelectorRow(
    lists: List<Watchlist>,
    activeId: String,
    onSelect: (String) -> Unit,
    onAddClick: () -> Unit,
    onDeleteClick: (String) -> Unit
){
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(lists, key = {it.id}) { list ->
            val selected = list.id == activeId
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if(selected) AccentBlue else BgPanel)
                    .clickable{ onSelect(list.id) }
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ){
                Text(list.name, style = MaterialTheme.typography.labelSmall, color = TextPrimary)
                if(selected && lists.size > 1){
                    Text("x", style = MaterialTheme.typography.labelSmall, color = TextPrimary,
                        modifier = Modifier.clickable{onDeleteClick(list.id) })
                }
            }
        }
        item{
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(BgInput)
                    .clickable{onAddClick()}
                    .padding(horizontal = 14.dp, vertical = 5.dp)
            ){
                Text(
                    "+ New",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextPrimary
                )
            }
        }
    }
}

@Composable
private fun NewWatchlistRow(name: String, onNameChange: (String) -> Unit, onConfirm: () -> Unit, onCancel: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(BgInput)
                .clickable{onCancel()}
                .padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            androidx.compose.foundation.text.BasicTextField(
                value = name,
                onValueChange = onNameChange,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextPrimary),
                decorationBox = {inner ->
                    if(name.isEmpty())
                        Text(
                            "Watchlist name...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextMuted
                        )
                    inner()
                }
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(BgInput)
                .clickable{onConfirm()}
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ){
            Text(
                "Add",
                style = MaterialTheme.typography.labelSmall,
                color = TextPrimary
            )
        }
        Box(
            modifier = Modifier.clip(RoundedCornerShape(8.dp))
                .background(BgInput)
                .clickable{onCancel()}
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                "Cancel",
                style = MaterialTheme.typography.labelSmall,
                color = TextPrimary
            )
        }
    }
}

@Composable
private fun WatchedItemRow(item: SkinPortItem, onRemove: () -> Unit){
    var priceToShow by remember { mutableIntStateOf(settings.priceToShowWatchlists) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BgPanel)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            item.marketHashName,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(BgInput)
                .clickable{priceToShow = Rswitch(priceToShow)}
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            val price = when (priceToShow) { 1 -> item.suggestedPrice; 2 -> item.medianPrice; else -> item.minPrice}
            val label = when (priceToShow) { 1 -> "Suggested"; 2 -> "Median"; else -> "Minimum"}
            Text(
                "$label: ${formatUsd(price)}",
                style = MaterialTheme.typography.labelSmall,
                color = PositiveGreen
            )
        }
        Text(
            "Remove",
            style = MaterialTheme.typography.labelSmall,
            color = NegativeRed,
            modifier = Modifier.clickable{onRemove()}
        )
    }
}

@Composable
private fun EmptyWatchlistCard(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BgPanel)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "This watchlist is empty",
            color = TextSecondary
        )
    }
}

@Composable
private fun InventoryAddRow(item: SteamInventoryItem, onAdd: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BgPanel)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            item.name,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )
        Text(
            "+ Add to Watchlist",
            style = MaterialTheme.typography.labelSmall,
            color = AccentBlue,
            modifier = Modifier.clickable{ onAdd() }
        )
    }
}


private fun Rswitch(swap :Int) :Int {
    if (swap == 1) return 2
    if (swap == 2) return 3
    return 1
}