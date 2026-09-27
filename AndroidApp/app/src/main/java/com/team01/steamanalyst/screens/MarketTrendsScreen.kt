package com.team01.steamanalyst.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.team01.steamanalyst.settings
import com.team01.steamanalyst.valuation.WatchlistManager
import kotlin.math.roundToInt


@Composable
fun MarketTrendsScreen(
    searchResult: List<SkinPortItem>,
    randomList: List<SkinPortItem>,
    selectedSkin: SkinPortItem?,
    summary: MarketSummary?,
    rankings: List<MarketplaceRanking>,
    isLoading: Boolean,
    onQueryChange: (String)-> Unit,
    onSkinSelected:(SkinPortItem) -> Unit,
    onBack: () -> Unit,
    onShuffle: () -> Unit,
    onAddToWatchlist: (SkinPortItem) -> Unit
){
var query by remember { mutableStateOf("") }
    val color = settings.colorScheme

    Column(Modifier.fillMaxSize()
        .background(color.background)) {
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
                if(query.isBlank()){
                    item{
                        Row(
                            Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Random Picks",
                                style = MaterialTheme.typography.titleSmall,
                                color = color.onBackground
                            )
                            Text(
                                "Shuffle",
                                style = MaterialTheme.typography.labelSmall,
                                color = color.primary,
                                modifier = Modifier.clickable{onShuffle()}
                            )
                        }
                    }
                    items(randomList, key = {it.marketHashName}) {result ->
                        SkinResultRow(result = result, onClick = {onSkinSelected(result)}, onAddToWatchlist = {onAddToWatchlist(result)})
                    }
                }else {
                    items(searchResult, key = { it.marketHashName }) { result ->
                        SkinResultRow(
                            result = result,
                            onClick = { onSkinSelected(result) },
                            onAddToWatchlist = { onAddToWatchlist(result) })
                    }
                }
                return@LazyColumn
            }
                item{
                    SelectedSkinHeader(
                        skin = selectedSkin,
                        dealRating = summary?.dealRating,
                        onBack = onBack
                    )}

            when{
                isLoading -> item{LoadingCard()}
                summary == null || summary.providerCount == 0 -> item {EmptyStateCard()}
                else -> {
                    item{MarketSummaryRow(summary)}
                    item{
                        Text(
                            "Marketplaces Low to High",
                            style = MaterialTheme.typography.titleSmall,
                            color = color.onBackground
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
private fun SkinResultRow(result: SkinPortItem, onClick: () -> Unit, onAddToWatchlist: ()-> Unit){
    val color = settings.colorScheme
    var isWatched by remember(result.marketHashName){
        mutableStateOf(
            WatchlistManager.getActiveWatchlist().itemHashNames.contains(result.marketHashName)
        )}
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable{onClick()}
            .background(color.surface)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            result.marketHashName,
            style = MaterialTheme.typography.bodyMedium,
            color = color.onSurface,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .width(130.dp)
                .height(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isWatched) color.surfaceVariant else color.primary)
                .clickable(enabled = !isWatched) {
                    onAddToWatchlist()
                    isWatched = true
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (isWatched)"Added" else "Add to Watchlist",
                style = MaterialTheme.typography.labelSmall,
                color = if (isWatched) color.onSurfaceVariant else color.onPrimary,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )
        }
    }
    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
}
@Composable
private fun SelectedSkinHeader(skin: SkinPortItem, dealRating: DealRating?, onBack: () -> Unit){
    val color = settings.colorScheme
    Row(
        modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(color.surface)
        .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back to search",
                tint = color.onSurfaceVariant
            )
        }
        Text(
            skin.marketHashName,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = color.onSurface,
            modifier = Modifier.weight(1f)
        )
        if(dealRating != null) {
            DealRatingChip(dealRating)
        }
    }
}

@Composable
private fun DealRatingChip(rating: DealRating){
    val color = settings.colorScheme
    val (label, chipColor) = when(rating){
        DealRating.GOOD_DEAL -> "Good Deal" to color.tertiary
        DealRating.FAIR_PRICE -> "Fair Price" to color.onSurfaceVariant
        DealRating.ABOVE_MARKET -> "Above Market" to color.error
        DealRating.INSUFFICIENT_DATA -> "No Data" to color.onSurfaceVariant
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(chipColor.copy(alpha = 0.15f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ){
        Text(label, style = MaterialTheme.typography.labelSmall, color = chipColor, fontWeight = FontWeight.Medium)
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
    val color = settings.colorScheme
    val valueColor = when(emphasis){
        StatEmphasis.POSITIVE -> color.tertiary
        StatEmphasis.NEGATIVE -> color.error
        StatEmphasis.NEUTRAL -> color.onSurface
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.surface)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = color.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.titleMedium, color = valueColor, fontWeight = FontWeight.Bold)
        if(sublabel != null){
            Text(sublabel, style = MaterialTheme.typography.labelSmall, color = color.onSurfaceVariant)
        }
    }
}

@Composable
private fun MarketplaceRow(ranking: MarketplaceRanking){
    val color = settings.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(color.surface)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "#${ranking.rank}",
            style = MaterialTheme.typography.labelSmall,
            color = color.onSurfaceVariant,
            modifier = Modifier.width(28.dp)
        )

        Column(Modifier.weight(1f)){
            Text(ranking.provider, style = MaterialTheme.typography.bodyMedium,color = color.onSurface, fontWeight = FontWeight.Medium)
            Text(
                "${formatPercent(ranking.percentDifferenceFromMedian)} vs median",
                style = MaterialTheme.typography.labelSmall,
                color = color.onSurfaceVariant
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(formatPrice(ranking.price),style = MaterialTheme.typography.bodyMedium,
                color = color.onSurface, fontWeight = FontWeight.SemiBold)
            DeltaLabel(ranking.rank, ranking.percentAboveCheapest)
        }
    }
}

@Composable
private fun DeltaLabel(rank: Int, percentAboveCheapest: Double){
    val color = settings.colorScheme
    val isCheapest = rank == 1
    val deltaColor = if(isCheapest) color.tertiary else color.error
    val icon = if(isCheapest) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward
    val text = if(isCheapest) "Cheapest" else "+${formatPercent(percentAboveCheapest)}"

    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)){
        Icon(icon, contentDescription = null, tint = deltaColor, modifier = Modifier.size(12.dp))
        Text(text, style = MaterialTheme.typography.labelSmall, color = deltaColor)
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
    val color = settings.colorScheme
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color.surface)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("No Marketplace data found for this Item", color = color.onSurfaceVariant)
    }
}

private fun formatPrice (value: Double?): String =
    if(value == null) "--" else formatUsd(value)

private fun formatPercent(value: Double): String =
    "${(value * 100).roundToInt() / 100.0}%"