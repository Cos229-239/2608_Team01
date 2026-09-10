package com.team01.steamanalyst.screens


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
import com.team01.steamanalyst.ui.theme.*
import java.util.Locale

@Composable
fun HomeScreen(){
    var query by remember {mutableStateOf("")}

    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()){
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
                Text("Market Trends", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
            }
            item{
                Text("Watchlist", style = MaterialTheme.typography.titleMedium)
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
private fun StatChip(value: String, label: String,modifier: Modifier = Modifier){
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
