package com.team01.steamanalyst.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.team01.steamanalyst.components.*
import com.team01.steamanalyst.data.MockData
import com.team01.steamanalyst.ui.theme.*
import java.util.Locale

@Composable
fun HomeScreen(){
    var query by remember {mutableStateOf("")}

    Column(Modifier.fillMaxSize()){
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
        Text(formatUsd(MockData.portfolioValue), style = MaterialTheme.typography.bodyMedium)
        Text(
            "Today's Change +${String.format(Locale.US, "%.2f", MockData.todaysChangePercent)}%",
            style = MaterialTheme.typography.labelSmall,
            color = PositiveGreen
        )
        Spacer(Modifier.height(8.dp))
        Text("Cash available", style = MaterialTheme.typography.labelSmall)
        Text(formatUsd(MockData.cashAvailable), style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(12.dp))

    }
}
