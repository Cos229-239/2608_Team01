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
import com.team01.steamanalyst.components.*
import com.team01.steamanalyst.data.MockData
import com.team01.steamanalyst.ui.theme.*
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
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)){
            Box( modifier = Modifier
                .background(Purple80)
                .width(90.dp)
                .height(125.dp)
                .clip(shape = RoundedCornerShape(6.dp))
                .background(BgPanel)
                .padding(vertical = 10.dp, horizontal = 8.dp))
            {
                Text("Profile Image", style = MaterialTheme.typography.labelSmall)
            }
            Box( modifier = Modifier
                .width(180.dp)
                .height(75.dp)
                .clip(shape = RoundedCornerShape(6.dp))
                .background(BgPanel)
                .padding(vertical = 10.dp, horizontal = 8.dp))
            {
                Text("Persona Name", style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
            }
        }
        Spacer(Modifier.height(24.dp))
    }

}

@Composable
fun ShowProfDetails(modifier: Modifier = Modifier) {
    var detailToShow :Int by remember { mutableIntStateOf(1) }

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
                Text("Settings", style = Typography.labelSmall, color = TextPrimary)
            }
            Box( modifier = Modifier
                .width(60.dp)
                .height(35.dp)
                .clip(shape = RoundedCornerShape(6.dp))
                .background(BgInput)
                .clickable(onClick = {detailToShow = 4})
                .padding(vertical = 10.dp, horizontal = 8.dp))
            {
                Text("Bank", style = Typography.labelSmall, color = TextPrimary)
            }
        }
    }

    Spacer(Modifier.height(8.dp))
    if (detailToShow == 1) {
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

            Text("Steam ID", style = MaterialTheme.typography.labelSmall, color = Purple40)
            Spacer(Modifier.height(8.dp))
            Text("Profile URL", style = MaterialTheme.typography.labelSmall, color = Purple40)
            Spacer(Modifier.height(8.dp))
            Text("Avatar URL", style = MaterialTheme.typography.labelSmall, color = Purple40)
            Spacer(Modifier.height(8.dp))
        }
    }
    else if (detailToShow == 2){
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
    else if (detailToShow == 3){
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
                "",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Text(
                "---------------------------------------------------------------------",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Text(
                "Settings stuff will be made soon",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }
    }
    else if (detailToShow == 4){
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