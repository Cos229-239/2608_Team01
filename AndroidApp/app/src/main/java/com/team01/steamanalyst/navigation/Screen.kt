package com.team01.steamanalyst.navigation

sealed class Screen (val route: String) {

    object Home: Screen(route = "home")

    object Inventory : Screen(route = "inventory")

    object MarketTrends: Screen(route = "market_trends")

    object Watchlists: Screen(route = "watchlists")

    object Profile : Screen(route = "profile")

}