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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team01.steamanalyst.components.*
import com.team01.steamanalyst.data.MockData
import com.team01.steamanalyst.ui.theme.*
import java.util.Locale

@Composable
@Preview
fun MarketScreen(){
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
                    ScreenCard(modifier = Modifier.weight(1.4f))
                }
            }
        }
    }
}

@Composable
private fun ScreenCard(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(BgPanel)
            .padding(4.dp)
    ) {

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
            Text("FirName\nSecName?", style = MaterialTheme.typography.labelSmall)
            Spacer(Modifier.width(5.dp))
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(75.dp)
                    .clip(shape = RoundedCornerShape(6.dp))
                    .background(BgPanel)
                    .padding(vertical = 30.dp)
            )
            {
                Text(
                    "Price",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary
                )
            }
            Spacer(Modifier.width(10.dp))
            Text("Seed", style = MaterialTheme.typography.labelSmall)
        }

    }

}