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
fun ProfileScreen(){
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
                    //ScreenCard(modifier = Modifier.weight(1.4f))
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
            .padding(16.dp)
    ) {
        Text("ExampleText", style = MaterialTheme.typography.labelSmall)
        Spacer(Modifier.height(8.dp))
        Text("Space Above", style = MaterialTheme.typography.labelSmall)
    }

}