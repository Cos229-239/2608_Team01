package com.team01.steamanalyst.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.team01.steamanalyst.components.*
import com.team01.steamanalyst.data.DealRating
import com.team01.steamanalyst.data.MarketSummary
import com.team01.steamanalyst.data.MarketplaceRanking
import com.team01.steamanalyst.data.SkinPortItem
import com.team01.steamanalyst.ui.theme.*
import kotlin.math.roundToInt


@Composable
fun MarketTrendsScreen(
    searchResult: List<SkinPortItem>,
    selectedSkin: SkinPortItem?,
    summary: MarketSummary?,
    rankings: List<MarketplaceRanking>,
    isLoading: Boolean,
    onQueryChange: (String)-> Unit,
    onSkinSelected:(SkinPortItem) -> Unit
){
var query by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize()) {
        TopSearchBar(
            query = query,
            onQueryChange = {
                query = it
                onQueryChange(it)
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
        ) {

            if(selectedSkin == null){
                items(searchResult){result->
                    SkinResultRow(result = result, onClick = {onSkinSelected(result)})
                }
                return@LazyColumn
            }
            item{SelectedSkinHeader(skin = selectedSkin, dealRating = summary?.dealRating)}

            when{
                isLoading -> item{LoadingCard()}
                summary == null || summary.providerCount == 0 -> item {EmptyStateCard()}
                else -> {
                    item{MarketSummaryRow(summary)}
                    item{
                        Text(
                            "Marketplaces Low to High",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextPrimary
                        )
                    }
                    items(rankings, key = {it.provider }) {ranking ->
                        MarketplaceRow(ranking)
                    }
                }
            }
        }
    }
}

@Composable
private fun SkinResultRow(result: SkinPortItem, onClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable{onClick()}
            .background(BgPanel)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Text(result.marketHashName, style = MaterialTheme.typography.bodyMedium)
    }
    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
}
@Composable
private fun SelectedSkinHeader(skin: SkinPortItem, dealRating: DealRating?){
    Row(
        modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(BgPanel)
        .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            skin.marketHashName,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )
        if(dealRating != null) {
            DealRatingChip(dealRating)
        }
    }
}

@Composable
private fun DealRatingChip(rating: DealRating){
    val (label, color) = when(rating){
        DealRating.GOOD_DEAL -> "Good Deal" to PositiveGreen
        DealRating.FAIR_PRICE -> "Fair Price" to Fair
        DealRating.ABOVE_MARKET -> "Above Market" to NegativeRed
        DealRating.INSUFFICIENT_DATA -> "No Data" to TextSecondary
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ){
        Text(label, style = MaterialTheme.typography.labelSmall, color = color, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun MarketSummaryRow(summary: MarketSummary){
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)){
            StatCard(
                modifier = Modifier.weight(1f),
                label = "Cheapest",
                value = formatPrice(summary.bestPrice),
                sublabel = summary.bestProvider,
                emphasis = StatEmphasis.POSITIVE
            )
            StatCard(
                modifier = Modifier.weight(1f),
                label = "Highest",
                value = formatPrice(summary.highestPrice),
                sublabel = summary.highestProvider,
                emphasis = StatEmphasis.NEGATIVE
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(
                modifier = Modifier.weight(1f),
                label = "Average",
                value = formatPrice(summary.averagePrice),
                sublabel = "${summary.providerCount} marketplace",
                emphasis = StatEmphasis.NEUTRAL
            )
            StatCard(
                modifier = Modifier.weight(1f),
                label = "Median",
                value = formatPrice(summary.medianPrice),
                sublabel = summary.bestPricePercentBelowMedian?.let{
                    "Cheapest is ${formatPercent(it)} below"
                },
                emphasis = StatEmphasis.NEUTRAL
            )
        }
    }
}

private enum class StatEmphasis{ POSITIVE, NEGATIVE, NEUTRAL}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    sublabel: String?,
    emphasis: StatEmphasis
){
    val valueColor = when(emphasis){
        StatEmphasis.POSITIVE -> PositiveGreen
        StatEmphasis.NEGATIVE -> NegativeRed
        StatEmphasis.NEUTRAL -> TextPrimary
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(BgPanel)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
        Text(value, style = MaterialTheme.typography.titleMedium, color = valueColor, fontWeight = FontWeight.Bold)
        if(sublabel != null){
            Text(sublabel, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
        }
    }
}

@Composable
private fun MarketplaceRow(ranking: MarketplaceRanking){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(BgPanel)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "#${ranking.rank}",
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary,
            modifier = Modifier.width(28.dp)
        )

        Column(Modifier.weight(1f)){
            Text(ranking.provider, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Text(
                "${formatPercent(ranking.percentDifferenceFromMedian)} vs median",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(formatPrice(ranking.price),style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            DeltaLabel(ranking.rank, ranking.percentAboveCheapest)
        }
    }
}

@Composable
private fun DeltaLabel(rank: Int, percentAboveCheapest: Double){
    val isCheapest = rank == 1
    val color = if(isCheapest) PositiveGreen else NegativeRed
    val icon = if(isCheapest) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward
    val text = if(isCheapest) "Cheapest" else "+${formatPercent(percentAboveCheapest)}"

    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)){
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(12.dp))
        Text(text, style = MaterialTheme.typography.labelSmall, color = color)
    }
}

@Composable
private fun LoadingCard(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyStateCard(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BgPanel)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("No Marketplace data found for this Item", color = TextSecondary)
    }
}

private fun formatPrice (value: Double?): String =
    if(value == null) "--" else formatUsd(value)

private fun formatPercent(value: Double): String =
    "${(value * 100).roundToInt() / 100.0}%"