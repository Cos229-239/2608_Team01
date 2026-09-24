package com.team01.steamanalyst.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.team01.steamanalyst.components.SideNav
import com.team01.steamanalyst.components.TopSearchBar
import com.team01.steamanalyst.screens.HomeScreen
import com.team01.steamanalyst.screens.InvenScreen
import com.team01.steamanalyst.screens.MarketScreen
import com.team01.steamanalyst.screens.WatchScreen
import com.team01.steamanalyst.screens.ProfileScreen
import com.team01.steamanalyst.screens.*
import com.team01.steamanalyst.ui.theme.BgRoot

@Composable
fun SteamAnalystNav(){
    // navContoller - Create/remember a navigation controller
    //backStackEntry/currentRoute - Observes the current back stack entry as a state
    //(recomposes whenever the navigation destination changes)
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: Screen.Home.route

    //Maps the raw route string back to a screen enum/sealed-class value
    val current = when (currentRoute){
        Screen.Inventory.route -> Screen.Inventory
        Screen.MarketTrends.route -> Screen.MarketTrends
        Screen.Watchlists.route -> Screen.Watchlists
        Screen.Profile.route -> Screen.Profile
        Screen.LogIn.route -> Screen.LogIn
        Screen.Settings.route -> Screen.Settings
        else -> Screen.Home

    }
    var query by remember {mutableStateOf("")}
    // Local helper: navigate to a screen with standard back-stack behavior
    fun navigate(screen: Screen){
        navController.navigate(screen.route){
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    Row(modifier = Modifier
        .fillMaxSize()
        .background(BgRoot)
    ){
        if(current != Screen.LogIn) {
            SideNav(current = current, onNavigate = { navigate(it) })
        }
        

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.fillMaxSize()
        ){
            composable (Screen.Home.route){HomeScreen() }
            composable (Screen.Inventory.route){InvenScreen()}
            composable (Screen.MarketTrends.route){MarketScreen()}
            composable (Screen.Watchlists.route){WatchScreen()}
            composable (Screen.Settings.route){SettingsScreen()}
            composable (Screen.Profile.route){
                ProfileScreen(onLoginClick = { navigate (Screen.LogIn)})
            }
            composable (Screen.LogIn.route){
                LogInScreen(
                    onLoginSuccess = {navigate (Screen.Home)},
                    onBack = {navController.popBackStack()}
                )
            }



        }

    }

}

