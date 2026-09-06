package com.team01.steamanalyst.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.team01.steamanalyst.ui.theme.*

@Composable
fun TopSearchBar(
    query: String,
    onQueryChange:(String) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Surface(
            modifier = Modifier
                .weight(1f)
                .height(40.dp),
            color = BgInput,
            shape = RoundedCornerShape(8.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(Modifier.width(8.dp))
                BasicTextFieldPlaceholder(query, onQueryChange)
            }
        }
    }
}

@Composable
private fun BasicTextFieldPlaceholder(query: String, onQueryChange: (String) -> Unit){
   androidx.compose.foundation.text.BasicTextField(
       value = query,
       onValueChange = onQueryChange,
       singleLine = true,
       textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextPrimary),
       decorationBox = {inner ->
           if(query.isEmpty()){
               Text("Search skins, knives, gloves ...", style = MaterialTheme.typography.bodyMedium, color = TextMuted)

           }
           inner()
       }
   )
}
