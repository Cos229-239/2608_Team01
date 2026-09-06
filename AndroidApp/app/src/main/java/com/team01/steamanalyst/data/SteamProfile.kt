package com.team01.steamanalyst.data

data class SteamProfile(
    val steamID: String = "",
    val personaName: String = "",
    val profileURL: String = "",
    val avatarURL: String = "",
    val communityVisibilityState: Int = 0,
    val personaState: Int = 0
)

object MockData{

    val portfolioValue =0.00

    val todaysChangePercent = 0.0

    val cashAvailable = 0.00

}