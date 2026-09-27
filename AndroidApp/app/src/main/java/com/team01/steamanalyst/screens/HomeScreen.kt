package com.team01.steamanalyst.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.team01.steamanalyst.components.*
import com.team01.steamanalyst.service.CatalogRepository
import com.team01.steamanalyst.ui.theme.*
import com.team01.steamanalyst.valuation.WatchlistManager
import com.team01.steamanalyst.watchlists
import java.util.Locale

@Composable
fun HomeScreen(){
    var query by remember {mutableStateOf("")}

    Column(modifier = Modifier
        .fillMaxSize()){
        TopSearchBar(query = query, onQueryChange = {query = it})

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ){
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    PortfolioCard(
                        modifier = Modifier.weight(1.4f),

                        )


                }
            }
            item{
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    InventorySummaryCard(modifier = Modifier.weight(1f),

                        )
                }
            }
            item{
                Text("Daily Movers", style = MaterialTheme.typography.titleMedium)
            }
            item{
                DailyMoverRow()
            }
            item{
                Text("Market Trends", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
            }
            item{
                MarketTrendsPreviewCard()
            }
            item{
                WatchlistPreviewCard()
            }

        }
    }
}

@Composable
private fun PortfolioCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(BgPanel)
            .padding(16.dp)
    ) {
        Text("Portfolio value", style = MaterialTheme.typography.titleSmall)
        Text(formatUsd(HomeMockData.portfolioValue), style = MaterialTheme.typography.bodyMedium)
        Text(
            "Today's Change +${String.format(Locale.US, "%.2f", HomeMockData.todaysChangePercent)}%",
            style = MaterialTheme.typography.labelSmall,
            color = PositiveGreen
        )
        Spacer(Modifier.height(8.dp))
        Text("Cash available", style = MaterialTheme.typography.labelSmall)
        Text(formatUsd(HomeMockData.cashAvailable), style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(12.dp))

    }
}

@Composable
private fun InventorySummaryCard(modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(BgPanel)
            .padding(5.dp)
    ){
        Text("Inventory Summary", style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
            StatChip("${HomeMockData.inventoryItemCount}", "Items", Modifier.weight(1f))
            StatChip(formatUsd(HomeMockData.InventoryValue), "Value", Modifier.weight(1f))
        }
        Spacer(Modifier.height(8.dp))
        StatChip("${HomeMockData.inventoryCategories}", "Categories", Modifier.fillMaxWidth())
        Spacer(Modifier.height(14.dp))

    }
}

@Composable
private fun StatChip(value: String, label: String,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(BgCard)
            .padding(vertical = 10.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(value, style = MaterialTheme.typography.titleSmall, color = TextPrimary)
        Text(label, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun DailyMoverRow(){
    val movers = CatalogRepository.topMovers. take(3)
    if(movers.isEmpty()){
        Text("Not enough data yet to show movers", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
        return
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)){
        movers.forEach { mover ->
            val isUp = mover.percentChange >= 0.0
            val color = if (isUp) PositiveGreen else NegativeRed
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(BgPanel)
                    .padding(10.dp)
            ) {
                Text(mover.marketHashName, style = MaterialTheme.typography.labelSmall, color = TextPrimary, maxLines = 1)
                Text(formatUsd(mover.currentPrice), style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                Text(
                    "${if (isUp) "+" else ""}${String.format(Locale.US, "%1f", mover.percentChange)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = color
                )

            }
        }
    }
}

@Composable
private fun MarketTrendsPreviewCard(){
    val preview = remember { CatalogRepository.randomSample(3) }
    Column(
        modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(BgPanel)
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if(preview.isEmpty()){
            Text("Loading market data . . .", color = TextSecondary, style = MaterialTheme.typography.labelSmall)
        }else{
            preview.forEach { skin ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                    Text(skin.marketHashName, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    Text(formatUsd(skin.medianPrice), style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                }
            }
        }

    }
}
@Composable
private fun WatchlistPreviewCard(){
   var activeId by remember { mutableStateOf(watchlists.activeListId) }
    val activeList = watchlists.lists.find { it.id == activeId } ?: watchlists.lists.first()
    val previewItems = activeList.itemHashNames
        .mapNotNull { hash -> CatalogRepository.currentCatalog.find {it.marketHashName == hash} }
        .take(3)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BgPanel)
            .padding(16.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            watchlists.lists.forEach { list ->
                val selected = list.id == activeId
                Text(
                    list.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = if(selected) AccentBlue else TextSecondary,
                    modifier = Modifier.clickable{
                        activeId = list.id
                        WatchlistManager.setActiveWatchlist(list.id)
                    }
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        if(previewItems.isEmpty()){
            Text(
                "No items in this watchlist yet"
                , style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
        }else{
            previewItems.forEach { skin ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        skin.marketHashName,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextPrimary
                    )
                    Text(
                        formatUsd(skin.medianPrice),
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}
