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
import com.team01.steamanalyst.apiKey
import com.team01.steamanalyst.components.*
import com.team01.steamanalyst.data.InventoryValuation
import com.team01.steamanalyst.data.SteamAccountData
import com.team01.steamanalyst.data.SteamInventoryItem
import com.team01.steamanalyst.data.SteamProfile
import com.team01.steamanalyst.data.ValuedInventoryItem
import com.team01.steamanalyst.steamAccount
import com.team01.steamanalyst.settings
import com.team01.steamanalyst.ui.theme.*
import com.team01.steamanalyst.valuation.MarketValuation
import com.team01.steamanalyst.vanityName
import java.util.Locale

@Composable
@Preview
fun ProfileScreen(){

    Column(Modifier.fillMaxSize()){

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ){
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ShowProf(modifier = Modifier.weight(1.4f))
                }
                    Spacer(Modifier.height(8.dp))
                    ShowProfDetails(modifier = Modifier.weight(1.4f))
            }
        }
    }
}

@Composable
fun ShowProf(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(BgPanel)
            .padding(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            if (steamAccount.loaded) {
                Box(
                    modifier = Modifier
                        .background(BgPanel)
                        .width(90.dp)
                        .height(125.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .background(BgPanel)
                        .padding(vertical = 10.dp, horizontal = 8.dp)
                )
                {
                    AsyncImage(
                        model = steamAccount.profile.profileURL,
                        contentDescription = "none",
                        modifier = Modifier.clip(CircleShape)
                    )
                }
                Box(
                    modifier = Modifier
                        .width(180.dp)
                        .height(75.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .background(BgPanel)
                        .padding(vertical = 10.dp, horizontal = 8.dp)
                )
                {
                    Text(
                        steamAccount.profile.personaName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary
                    )
                }
            }
            else {
                Box(
                    modifier = Modifier
                        .background(Purple80)
                        .width(300.dp)
                        .height(125.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .background(BgPanel)
                        .padding(vertical = 10.dp, horizontal = 8.dp)
                )
                {
                    Text("Log in to view profile", style = MaterialTheme.typography.bodyLarge, color = TextPrimary)
                }
            }
        }
        Spacer(Modifier.height(24.dp))
    }

}

@Composable
fun ShowProfDetails(modifier: Modifier = Modifier) {
    var detailToShow :Int by remember { mutableIntStateOf(1) }
    var priceToShow by remember { mutableIntStateOf(settings.priceToShowWatchlists) }
    var showDetails :Boolean by remember { mutableStateOf(settings.showDetailsInventory) }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(BgInput)
            .padding(8.dp)
    )
    {
        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)){
            Box( modifier = Modifier
                .width(90.dp)
                .height(35.dp)
                .clip(shape = RoundedCornerShape(6.dp))
                .background(BgInput)
                .clickable(onClick = {detailToShow = 1})
                .padding(vertical = 10.dp, horizontal = 8.dp))
            {
                Text("Personal info", style = MaterialTheme.typography.labelSmall, color = TextPrimary)
            }

            Box( modifier = Modifier
                .width(90.dp)
                .height(35.dp)
                .clip(shape = RoundedCornerShape(6.dp))
                .background(BgInput)
                .clickable(onClick = {detailToShow = 2})
                .padding(vertical = 10.dp, horizontal = 8.dp))
            {
                Text("Notifications", style = MaterialTheme.typography.labelSmall, color = TextPrimary)
            }
            Box( modifier = Modifier
                .width(60.dp)
                .height(35.dp)
                .clip(shape = RoundedCornerShape(6.dp))
                .background(BgInput)
                .clickable(onClick = {detailToShow = 3})
                .padding(vertical = 10.dp, horizontal = 8.dp))
            {
                Text("Settings", style = MaterialTheme.typography.labelSmall, color = TextPrimary)
            }
            Box( modifier = Modifier
                .width(60.dp)
                .height(35.dp)
                .clip(shape = RoundedCornerShape(6.dp))
                .background(BgInput)
                .clickable(onClick = {detailToShow = 4})
                .padding(vertical = 10.dp, horizontal = 8.dp))
            {
                Text("Bank", style = MaterialTheme.typography.labelSmall, color = TextPrimary)
            }
        }
    }

    Spacer(Modifier.height(8.dp))
    if (detailToShow == 1) { //Show the prof details - where Login should go
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .background(BgPanel)
                .padding(16.dp)
        )
        {
            Text(
                "Profile Information",
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )
            Text(
                "change your personal information here",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Text(
                "---------------------------------------------------------------------",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            if (steamAccount.loaded) { //Show account info
                Text(steamAccount.profile.steamID, style = MaterialTheme.typography.labelSmall, color = Purple40)
                Spacer(Modifier.height(8.dp))
                val commvis :Int = steamAccount.profile.communityVisibilityState
                Text("Community Visibile: $commvis", style = MaterialTheme.typography.labelSmall, color = Purple40)
                Spacer(Modifier.height(8.dp))
                val perstate :Int = steamAccount.profile.personaState
                Text("Persona State: $perstate", style = MaterialTheme.typography.labelSmall, color = Purple40)
                Spacer(Modifier.height(20.dp))
                Box( modifier = Modifier //Sign out button
                    .background(BgPanel)
                    .width(120.dp)
                    .height(45.dp)
                    .clip(shape = RoundedCornerShape(6.dp))
                    .clickable(onClick = {SignOut()})
                    .background(AccentBlue)
                    .padding(vertical = 10.dp, horizontal = 8.dp))
                {
                    Text("Sign Out", style = MaterialTheme.typography.bodyLarge)
                }
            }
            else { //Login button
                Spacer(Modifier.height(16.dp))
                Box( modifier = Modifier
                    .background(BgPanel)
                    .width(120.dp)
                    .height(45.dp)
                    .clip(shape = RoundedCornerShape(6.dp))
                    .clickable(onClick = {FakeProfile()}) //<- load fake profile for now - should send you to login screen
                    .background(AccentBlue)
                    .padding(vertical = 10.dp, horizontal = 8.dp))
                {
                    Text("Log In", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
    else if (detailToShow == 2){ //Notifications
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .background(BgPanel)
                .padding(16.dp)
        )
        {
            Text(
                "Notifications",
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )
            Text(
                "see and manage what's been going on",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Text(
                "---------------------------------------------------------------------",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Text(
                "No Notifications yet",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
    }
    else if (detailToShow == 3){ //Settings - Already have ideas for this
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .background(BgPanel)
                .padding(16.dp)
        )
        {
            Text(
                "Settings",
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )
            Text(
                "change certain UI elements here...",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Text(
                "---------------------------------------------------------------------",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box( modifier = Modifier
                        .background(BgPanel)
                        .width(130.dp)
                        .height(25.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .clickable(onClick = {
                            priceToShow = Rswitch(priceToShow)
                            settings.priceToShowWatchlists = priceToShow
                        })
                        .background(AccentBlue)
                        .padding(vertical = 5.dp, horizontal = 7.dp))
                    {
                        Text("Price in Watchlist", style = MaterialTheme.typography.bodySmall)
                    }
                    Box( modifier = Modifier
                        .background(BgPanel)
                        .width(80.dp)
                        .height(25.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .background(BgPanel)
                        .padding(vertical = 5.dp, horizontal = 7.dp))
                    {
                        val showing = when(priceToShow){
                            2 -> "Median"
                            3 -> "Minimum"
                            else -> "Suggested"
                        }
                        Text(showing, style = MaterialTheme.typography.bodySmall, color = Purple40)
                    }
                }
            Spacer(Modifier.height(8.dp))
                    Box( modifier = Modifier
                        .background(BgPanel)
                        .width(225.dp)
                        .height(25.dp)
                        .clip(shape = RoundedCornerShape(6.dp))
                        .clickable(onClick = {
                            showDetails = !showDetails
                            settings.showDetailsInventory = showDetails
                        })
                        .background(AccentBlue)
                        .padding(vertical = 5.dp, horizontal = 7.dp))
                    {
                        val showing = when(showDetails){
                            true -> "details"
                            else -> "picture"
                        }
                        Text("Show $showing in inventory by default", style = MaterialTheme.typography.bodySmall)
                    }

        }
    }
    else if (detailToShow == 4){ //Bank info - Not sure if this will stay in final
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .background(BgPanel)
                .padding(16.dp)
        )
        {
            Text(
                "Banks",
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )
            Text(
                "manage your bank accounts and view transactions",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Text(
                "-----------------------------------------------------------------------------------------",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Text(
                "Bank stuff will be made soon",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
    }
}

fun FakeProfile(){
    val prof :SteamProfile = SteamProfile("MyID", "DatFatCat77", "https://freepngimg.com/download/painting/84774-square-art-pixel-rectangle-cat-hd-image-free-png.png", "wwwdotAvatardotgov", 1, 1)
    val inve :List<SteamInventoryItem> = listOf(
        SteamInventoryItem("001", "101", "Inst1", 5, "TheOne", "FirstOfAll", "Won", true, false, "https://i.pinimg.com/736x/31/af/0a/31af0a4aae975826c47842210a43a62b.jpg"),
        SteamInventoryItem("002", "202", "Inst2", 4, "SecondComing", "Secondly", "Too", true, true, "https://img.magnific.com/premium-vector/pixelated-sword-icon_475147-3919.jpg"),
        SteamInventoryItem("003", "303", "Inst3", 9, "RunnerUp", "AndLastly", "tree", false, false, "https://www.shutterstock.com/image-vector/pixel-purple-arrow-8-bit-260nw-2613475247.jpg"),
        SteamInventoryItem("005", "505", "Inst5", 12, "Skipper", "FinaleNOT", "FiveMind", true, false, "https://static.vecteezy.com/system/resources/previews/061/889/217/non_2x/pixelated-earth-globe-icon-global-connectivity-international-relations-and-environmental-awareness-symbol-retro-style-world-representation-isolated-illustration-vector.jpg"),
        SteamInventoryItem("004", "404", "Inst4", 6, "SoLazy", "Point__.", "ForWhat", true, true, "https://www.shutterstock.com/image-vector/sleeping-cat-icon-pixel-8-260nw-2662730963.jpg"),
    )
    val item1 = SteamInventoryItem("045", "213", "InstaK", 1, "UltraPets", "Purrs", "meowsy", true, false, "https://www.shutterstock.com/image-vector/pixel-art-cute-cat-face-260nw-2754859093.jpg")
    val item2 = SteamInventoryItem("024", "312", "InstaL", 1, "Video", "Game", "wwwcom", true, true, "https://static.vecteezy.com/system/resources/thumbnails/020/577/584/small/white-stick-controller-in-pixel-art-style-vector.jpg")
    val item3 = SteamInventoryItem("079", "132", "InstaW", 1, "Playableish", "IThink", "hope", false, false, "https://previews.123rf.com/images/vectorman92/vectorman921806/vectorman92180600095/102613555-letter-w-colorful-pixel-art-alphabet-typeface-with-shadow-vector-typography-design.jpg")
    val item5 = SteamInventoryItem("011", "321", "InstaG", 1, "PureVibes", "DaTimes", "Nastguj", true, false, "https://static.vecteezy.com/system/resources/thumbnails/024/321/151/small/sleep-pixel-perfect-gradient-linear-ui-icon-sleeping-mode-muted-sound-relaxation-time-bedtime-line-color-user-interface-symbol-modern-style-pictogram-isolated-outline-illustration-vector.jpg")
    val item4 = SteamInventoryItem("040", "123", "Insta1", 1, "LazyPlus", "Plush", "Comfort", true, true, "https://img.magnific.com/premium-vector/pixel-art-comfort-bed-furniture-icon-illustration-vector-game-design_1038602-2357.jpg")
    val lisVal : List<ValuedInventoryItem> = listOf(
        ValuedInventoryItem(item1, 12.99, 5.89, 9.99, false),
        ValuedInventoryItem(item2, 23.89, 22.00, 22.29, false),
        ValuedInventoryItem(item3, 35.59, 22.99,33.19,  true),
        ValuedInventoryItem(item5, 53.89, 17.49, 40.99, false),
        ValuedInventoryItem(item4, 45.59, 27.99, 62.29, true),
    )
    val daVal : InventoryValuation = InventoryValuation(lisVal, 0, 1, 1.01)
    steamAccount = SteamAccountData(prof, inve, daVal, true)
}
private fun Rswitch(swap :Int) :Int {
    if (swap == 1) return 2
    if (swap == 2) return 3
    return 1
}
fun SignOut(){
    steamAccount = SteamAccountData()
    vanityName = ""
    apiKey = ""
}