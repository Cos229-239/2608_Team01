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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.team01.steamanalyst.components.*
import com.team01.steamanalyst.data.ValuedInventoryItem
import com.team01.steamanalyst.steamAccount
import com.team01.steamanalyst.settings
import com.team01.steamanalyst.ui.theme.*

@Composable
@Preview
fun WatchScreen(){
    var query by remember {mutableStateOf("")}
    var spaced :Boolean by remember { mutableStateOf(true) }
    var sortOrder :String by remember { mutableStateOf(settings.SortByWatchlists) }
    var ascending : Boolean by remember { mutableStateOf(settings.AscendingWatchlists) }
    Column(Modifier.fillMaxSize()){
        TopSearchBar(query = query, onQueryChange = {query = it})
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Spacer(Modifier.width(3.dp))
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height((40.dp))
                    .padding(vertical = 3.dp)
                    .clip(shape = RoundedCornerShape(6.dp))
                    .background(Purple40)
                    .padding(vertical = 10.dp, horizontal = 8.dp)
                    .clickable(onClick = {
                        ascending = !ascending
                        settings.AscendingWatchlists = ascending
                    })
            ) {
                if (ascending) Text("Ascending", style = MaterialTheme.typography.labelSmall)
                else Text("Descending", style = MaterialTheme.typography.labelSmall)
            }
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height((40.dp))
                    .padding(vertical = 3.dp)
                    .clip(shape = RoundedCornerShape(6.dp))
                    .background(Purple40)
                    .padding(vertical = 10.dp, horizontal = 8.dp)
                    .clickable(onClick = {
                        sortOrder = switchSort(sortOrder)
                        settings.SortByWatchlists = sortOrder
                    })
            ) {
                Text("Sort Type: $sortOrder", style = MaterialTheme.typography.labelSmall)
            }
        }
        Spacer(Modifier.height(10.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ){
           val listItems : List<ValuedInventoryItem>
            if (ascending){
                listItems = when(sortOrder){
                    "Name" -> steamAccount.valuation.items.sortedBy { it.steamItem.name }
                    "MarketName" -> steamAccount.valuation.items.sortedBy { it.steamItem.marketName }
                    "Price(Sugg)" -> steamAccount.valuation.items.sortedBy { it.suggestedPrice }
                    "Price(Med)" -> steamAccount.valuation.items.sortedBy { it.medianPrice }
                    "Price(Min)" -> steamAccount.valuation.items.sortedBy { it.minPrice }
                    else -> steamAccount.valuation.items
                }
            }
            else {
                listItems = when(sortOrder){
                    "Name" -> steamAccount.valuation.items.sortedByDescending { it.steamItem.name }
                    "MarketName" -> steamAccount.valuation.items.sortedByDescending { it.steamItem.marketName }
                    "Price(Sugg)" -> steamAccount.valuation.items.sortedByDescending { it.suggestedPrice }
                    "Price(Med)" -> steamAccount.valuation.items.sortedByDescending { it.medianPrice }
                    "Price(Min)" -> steamAccount.valuation.items.sortedByDescending { it.minPrice }
                    else -> steamAccount.valuation.items
                }
            }
            item {
                    for (item in listItems) {
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                           spaced = rShowItem(modifier = Modifier.weight(1.4f), item, query)
                        }
                        if (spaced) Spacer(Modifier.height(10.dp))
                    }
            }
        }
    }
}

@Composable
fun rShowItem(modifier: Modifier = Modifier, item : ValuedInventoryItem, query :String) :Boolean {
    var priceToShow by remember { mutableIntStateOf(settings.priceToShowWatchlists) }
    if (query != "") {
        if (!item.steamItem.name.contains(query, true) and !item.steamItem.marketName.contains(query, true)) return false
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(BgPanel)
            .padding(4.dp)
    )
    {

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically){

            Box(
                modifier = Modifier
                    .width(90.dp)
                    .height(75.dp)
                    .clip(shape = RoundedCornerShape(6.dp))
                    .background(BgPanel)
                    .padding(vertical = 10.dp, horizontal = 8.dp)
            )
            {
                //Code for the picture goes here
                AsyncImage(
                    model = item.steamItem.iconUrl,
                    contentDescription = "none",
                    modifier = Modifier.clip(CircleShape)
                )
            }

            Text(item.steamItem.name + "\n" + item.steamItem.marketName, style = MaterialTheme.typography.labelSmall, color = Purple40)
            Spacer(Modifier.width(5.dp))

            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(75.dp)
                    .clip(shape = RoundedCornerShape(6.dp))
                    .background(BgPanel)
                    .padding(vertical = 30.dp)
                    .clickable(onClick = {priceToShow = Rswitch(priceToShow)})
            )
            {
                val price :Double = when (priceToShow){
                    1 -> item.suggestedPrice
                    2 -> item.medianPrice
                    else -> item.minPrice
                }
                val typePrice :String = when (priceToShow){
                    1 -> "Suggested: "
                    2 -> "Median: "
                    else -> "Minimum: "
                }

                Text(
                    typePrice + "$price",
                    style = MaterialTheme.typography.labelSmall,
                    color = PositiveGreen
                )
            }

            /*Spacer(Modifier.width(10.dp))
            Text("Seed", style = MaterialTheme.typography.labelSmall, color = Purple40)*/
        }

    }
return true
}
private fun Rswitch(swap :Int) :Int {
    if (swap == 1) return 2
    if (swap == 2) return 3
    return 1
}
private fun switchSort(sortOrder:String):String{
    if (sortOrder == "NoSort") return "Name"
    if (sortOrder == "Name") return "MarketName"
    if (sortOrder == "MarketName") return "Price(Sugg)"
    if (sortOrder == "Price(Sugg)") return "Price(Med)"
    if (sortOrder == "Price(Med)") return "Price(Min)"
    return "NoSort"
}