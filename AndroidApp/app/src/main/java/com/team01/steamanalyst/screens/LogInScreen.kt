package com.team01.steamanalyst.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team01.steamanalyst.apiKey
import com.team01.steamanalyst.service.SteamAccountService
import com.team01.steamanalyst.steamAccount
import com.team01.steamanalyst.ui.theme.*
import com.team01.steamanalyst.vanityName

@Composable
fun LogInScreen(onLoginSuccess: () -> Unit, onBack: () -> Unit = {}){
    var gettingName : Boolean by remember { mutableStateOf(true) }
    var gettingKey : Boolean by remember { mutableStateOf(false) }
    var loaded : Boolean by remember { mutableStateOf(steamAccount.loaded) }
    var query by remember { mutableStateOf("") }
    LaunchedEffect(loaded) {
        if (loaded) {
            onLoginSuccess()
        }
    }
    Column(Modifier.fillMaxSize()) {
        LoginSpace(query = query, onQueryChange = { query = it })
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)){
                    if (gettingName) Text("Enter vanity name above", style = MaterialTheme.typography.bodySmall, color = TextPrimary)
                    else if (gettingKey) Text("Enter api key above", style = MaterialTheme.typography.bodySmall, color = TextPrimary)
                    else Text("If this information is correct, press Enter", style = MaterialTheme.typography.bodySmall, color = TextPrimary)
                }
                Spacer(Modifier.height(20.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)){
                    Text("Vanity Name", style = MaterialTheme.typography.bodySmall, color = TextPrimary)
                    Spacer(Modifier.width(100.dp))
                    Text("Api Key", style = MaterialTheme.typography.bodySmall, color = TextPrimary)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)){
                    Box( modifier = Modifier
                        .background(BgPanel)
                        .width(160.dp)
                        .height(45.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .clickable(onClick = {
                            gettingName = true
                            gettingKey = false
                        })
                        .background(BgInput)
                        .padding(vertical = 10.dp, horizontal = 8.dp))
                    {
                        Text(vanityName, style = MaterialTheme.typography.bodySmall, color = Purple40)
                    }
                    Box( modifier = Modifier
                        .background(BgPanel)
                        .width(160.dp)
                        .height(45.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .clickable(onClick = {
                            gettingKey = true
                            gettingName = false
                        })
                        .background(BgInput)
                        .padding(vertical = 10.dp, horizontal = 8.dp))
                    {
                        Text(apiKey, style = MaterialTheme.typography.bodySmall, color = Purple40)
                    }
                }
                Spacer(Modifier.height(160.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box( modifier = Modifier
                        .background(BgPanel)
                        .width(120.dp)
                        .height(45.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .clickable(onClick = onBack)
                        .background(BgInput)
                        .padding(vertical = 10.dp, horizontal = 8.dp))
                    {
                        Text("Back", style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
                    }
                    Box( modifier = Modifier
                        .background(BgPanel)
                        .width(120.dp)
                        .height(45.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .clickable(onClick = {
                            loaded = checker(gettingName, gettingKey, query)
                            gettingName = false
                            gettingKey = false
                            query = ""
                        })
                        .background(AccentBlue)
                        .padding(vertical = 10.dp, horizontal = 8.dp))
                    {
                        Text("Enter", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

        }

    }
}

fun checker(gettingName :Boolean, gettingKey :Boolean, query :String) :Boolean {
    if (gettingName){
        vanityName = query
        return false
    }
    if (gettingKey){
        apiKey = query
        return false
    }
    if ((vanityName == "Fake") and (apiKey == "Fake")){ FakeProfile() }
    else steamAccount = SteamAccountService().loadAccount(vanityName, apiKey)
    return true
}

@Composable
fun LoginSpace(
    query: String,
    onQueryChange:(String) -> Unit,
    modifier: Modifier = Modifier
){
    // Horizontal container for the whole search bar row
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
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
            //Inner row to hold the text field, padded inside the surface
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(Modifier.width(8.dp))
                LoginTextGetter(query, onQueryChange)
            }
        }
    }
}

@Composable
// editable text field with manual placeholder logic
fun LoginTextGetter(query: String, onQueryChange: (String) -> Unit){
    androidx.compose.foundation.text.BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextPrimary),
        decorationBox = {inner ->
            if(query.isEmpty()){
                Text("Enter Login information here...", style = MaterialTheme.typography.bodyMedium, color = TextMuted)

            }
            inner()
        }
    )
}