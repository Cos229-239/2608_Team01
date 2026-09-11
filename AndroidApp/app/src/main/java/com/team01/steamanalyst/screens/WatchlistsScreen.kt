package com.team01.steamanalyst.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team01.steamanalyst.MainActivity
import com.team01.steamanalyst.components.*
import com.team01.steamanalyst.data.InventoryValuation
import com.team01.steamanalyst.data.SteamAccountData
import com.team01.steamanalyst.data.SteamInventoryItem
import com.team01.steamanalyst.data.ValuedInventoryItem
import com.team01.steamanalyst.service.SteamAccountService
import com.team01.steamanalyst.ui.theme.*
import java.util.Locale

@Composable
@Preview
fun WatchScreen(){
    var query by remember {mutableStateOf("")}

    val mockItem = SteamInventoryItem("","","",1,"DaItem", "OfDoom")
    val mockVal1 = ValuedInventoryItem(mockItem, 5.5, 2.4, 4.0)
    val mockVal2 = ValuedInventoryItem(mockItem, 6.89, 4.19, 5.79)
    val mockVal3 = ValuedInventoryItem(mockItem, 3.99, 1.49, 3.00)

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
                /*
                    for (item in steamAccount.valuation.items) {
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            RShowItem(modifier = Modifier.weight(1.4f), item)
                        }
                    }
                */
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                   RShowItem(modifier = Modifier.weight(1.4f), mockVal1)
                }
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                   RShowItem(modifier = Modifier.weight(1.4f), mockVal2)
                }
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                   RShowItem(modifier = Modifier.weight(1.4f), mockVal3)
                }
            }
        }
    }
}

@Composable
fun RShowItem(modifier: Modifier = Modifier, item : ValuedInventoryItem){
    var priceToShow by remember { mutableIntStateOf(1) }

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
                    .padding(vertical = 30.dp, horizontal = 8.dp)
            )
            {
                //Code for the picture goes here
                Text(
                    text = "PictureHere",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }

            Text(item.steamItem.name + "\n" + item.steamItem.marketName, style = MaterialTheme.typography.labelSmall, color = Purple40)
            Spacer(Modifier.width(5.dp))

            Box(
                modifier = Modifier
                    .width(90.dp)
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

}
fun Rswitch(swap :Int) :Int {
    if (swap == 1) return 2
    if (swap == 2) return 3
    return 1
}
