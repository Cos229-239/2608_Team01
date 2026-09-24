package com.team01.steamanalyst.screens


import android.R
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*
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
import com.team01.steamanalyst.settings
import com.team01.steamanalyst.ui.theme.*
import java.util.Locale

@Composable
fun HomeScreen(){
    var query by remember {mutableStateOf("")}

    Column(modifier = Modifier
        .fillMaxSize()
        .background(settings.colorScheme.background)){
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
                Text("Daily Movers", style = MaterialTheme.typography.titleMedium, color = settings.colorScheme.onBackground)
            }
            item{
                DailyMoverRow()
            }
            item{
                Text("Market Trends", style = MaterialTheme.typography.titleMedium, color = settings.colorScheme.onBackground)
                Spacer(Modifier.height(8.dp))
            }
            item{
                Text("Watchlist", style = MaterialTheme.typography.titleMedium, color = settings.colorScheme.onBackground)
            }

        }
    }
}

@Composable
private fun PortfolioCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(settings.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text("Portfolio value", style = MaterialTheme.typography.titleSmall, color = settings.colorScheme.onSurface)
        Text(formatUsd(HomeMockData.portfolioValue), style = MaterialTheme.typography.bodyMedium, color = settings.colorScheme.onSurface)
        Text(
            "Today's Change +${String.format(Locale.US, "%.2f", HomeMockData.todaysChangePercent)}%",
            style = MaterialTheme.typography.labelSmall,
            color = settings.colorScheme.tertiary
        )
        Spacer(Modifier.height(8.dp))
        Text("Cash available", style = MaterialTheme.typography.labelSmall, color = PurpleGrey40)
        Text(formatUsd(HomeMockData.cashAvailable), style = MaterialTheme.typography.bodyMedium, color = settings.colorScheme.onSurface)
        Spacer(Modifier.height(12.dp))

    }
}

@Composable
private fun InventorySummaryCard(modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(settings.colorScheme.surface)
            .padding(5.dp)
    ){
        Text("Inventory Summary", style = MaterialTheme.typography.titleSmall, color = settings.colorScheme.onSurface)
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
            .background(settings.colorScheme.primaryContainer)
            .padding(vertical = 10.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(value, style = MaterialTheme.typography.titleSmall, color = settings.colorScheme.onSurface)
        Text(label, style = MaterialTheme.typography.labelSmall, color = settings.colorScheme.surface)
    }
}

@Composable
private fun DailyMoverRow(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

    }
}

@Composable
private fun WatchlistPreviewRow(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(settings.colorScheme.surface)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

    }
}
