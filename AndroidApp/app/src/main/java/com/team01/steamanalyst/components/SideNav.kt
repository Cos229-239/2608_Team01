package com.team01.steamanalyst.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.team01.steamanalyst.navigation.Screen
import com.team01.steamanalyst.ui.theme.*


@Composable
fun SideNav(
    current: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
){
    // Defines the nav items as (destination, label) pairs
    val items = listOf(
        Screen.Home to "Home",
        Screen.Home to "Inventory",
        Screen.Home to "Market Trends",
        Screen.Home to "Watchlists",
        Screen.Home to "Profile"
    )
    // Draws a vertical container for the whole sidebar
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(96.dp)
            .background(BgPanel)
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ){
       // Loop through each nav item and draws a row for it
        items.forEach{(screen, label) ->
            val selected = screen == current
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp)
                    .clip(shape= RoundedCornerShape(6.dp))
                    .background(BgPanel)
                    .clickable {onNavigate(screen)}
                    .padding(vertical = 10.dp, horizontal = 8.dp)
            ){
                Text(
                    text=label,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (selected) AccentBlue else TextSecondary
                )
            }
        }
    }
}